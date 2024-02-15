package com.example.ekohort_android.presentation.ibu

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ekohort_android.databinding.ActivityListIbuBinding
import com.example.ekohort_android.domain.Result
import com.example.ekohort_android.domain.ibu.model.Ibu
import com.example.ekohort_android.presentation.base.BaseActivity
import com.example.ekohort_android.presentation.ibu.form.DataIbuAwalActivity
import com.example.ekohort_android.utils.exts.toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListIbuActivity : BaseActivity<ActivityListIbuBinding>() {
    private val viewModel: ListIbuViewModel by viewModel()
    private val adapter by lazy {
        ListIbuAdapter<Ibu, Ibu> {
            MaterialAlertDialogBuilder(this@ListIbuActivity)
                .setMessage("Are you sure?")
                .setPositiveButton(android.R.string.ok) {_, _ ->
                    viewModel.delete(it)
                }
                .setNegativeButton(android.R.string.cancel, null)
                .show()
        }
    }

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
                                    if (it.data == null) {
                                        adapter.submitList(null)
                                    }
                                    adapter.submitList(it.data ?: emptyList())
                                }
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
        this.adapter = this@ListIbuActivity.adapter
    }

    private val startForResult = registerForActivityResult(StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            viewModel.fetchData()
        }
    }
}