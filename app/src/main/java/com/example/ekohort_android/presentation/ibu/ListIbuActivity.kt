package com.example.ekohort_android.presentation.ibu

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ekohort_android.databinding.ActivityListIbuBinding
import com.example.ekohort_android.domain.ibu.model.Ibu
import com.example.ekohort_android.domain.Result
import com.example.ekohort_android.presentation.base.BaseActivity
import com.example.ekohort_android.presentation.ibu.form.DataIbuAwalActivity
import com.example.ekohort_android.utils.exts.toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import java.util.*

class ListIbuActivity : BaseActivity<ActivityListIbuBinding>() {
    private val viewModel by lazy { ListIbuViewModel(get()) }
    private val dataList = ArrayList<Ibu>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListIbuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            rvItemIbu.showRecyclerList()

            lifecycleScope.launch {
                lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    launch {
                        viewModel.data.collect {
                            loading.isVisible = it is Result.Idle && it.firstLoad
                            rvItemIbu.isVisible = it !is Result.Idle

                            when (it) {
                                is Result.Idle -> {
                                    // TODO: Add loading?
                                }

                                is Result.Error -> {
                                    it.exception?.let { exc ->
                                        toast("${it.message} ${exc::class.qualifiedName}")
                                        true
                                    } ?: toast(it.message)
                                }

                                is Result.Success -> {
                                    dataList.clear()
                                    it.data?.let(dataList::addAll)
                                }
                            }
                        }
                    }

                    launch {
                        viewModel.manipState.collect {
                            when (it) {
                                is Result.Success -> viewModel::fetchData
                                is Result.Error -> {
                                    it.exception?.let { exc ->
                                        toast("${it.message} ${exc::class.qualifiedName}")
                                        true
                                    } ?: toast(it.message)
                                }

                                else -> {}
                            }
                        }
                    }
                }
            }

            btnAddData.setOnClickListener {
                val intent = Intent(this@ListIbuActivity, DataIbuAwalActivity::class.java)
                startForResult.launch(intent)
            }
        }
    }

    private fun RecyclerView.showRecyclerList() {
        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            this.layoutManager = GridLayoutManager(this@ListIbuActivity, 2)
        } else{
            this.layoutManager = LinearLayoutManager(this@ListIbuActivity)
        }
        val adapter = ListIbuAdapter(dataList) {
            MaterialAlertDialogBuilder(this@ListIbuActivity)
                .setMessage("Are you sure?")
                .setPositiveButton(android.R.string.ok) {_, _ ->
                    viewModel.delete(it)
                }
                .setNegativeButton(android.R.string.cancel, null)
                .show()
        }
        this.adapter = adapter
    }

    private val startForResult = registerForActivityResult(StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            viewModel.fetchData()
        }
    }
}