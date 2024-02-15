package com.example.ekohort_android.presentation.ibu

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.coroutineScope
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
            rvItemIbu.setHasFixedSize(true)
            rvItemIbu.showRecyclerList()

            lifecycle.coroutineScope.launch {
                viewModel.data.collectLatest {
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

                viewModel.manipState.collectLatest {
                    when (it) {
                        is Result.Success -> viewModel::fetchData
                        else -> {}
                    }
                }
            }

            btnAddData.setOnClickListener {
                val intent = Intent(this@ListIbuActivity, DataIbuAwalActivity::class.java)
                startActivity(intent)
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
                .setNeutralButton(android.R.string.cancel, null)
        }
        this.adapter = adapter
    }
}