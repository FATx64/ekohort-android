package com.example.ekohort_android.presentation.nakes

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ekohort_android.R
import com.example.ekohort_android.databinding.ActivityListIbuBinding
import com.example.ekohort_android.domain.Result
import com.example.ekohort_android.domain.nakes.model.Nakes
import com.example.ekohort_android.presentation.base.BaseActivity
import com.example.ekohort_android.presentation.ibu.ListIbuAdapter
import com.example.ekohort_android.presentation.nakes.detail.DetailNakesActivity
import com.example.ekohort_android.presentation.nakes.form.DataNakesAwalActivity
import com.example.ekohort_android.utils.exts.toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListNakesActivity : BaseActivity<ActivityListIbuBinding>() {
    private val viewModel: ListNakesViewModel by viewModel()
    private val adapter by lazy {
        ListIbuAdapter<Nakes, Nakes>({
            MaterialAlertDialogBuilder(this@ListNakesActivity)
                .setMessage("Are you sure?")
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    viewModel.delete(it)
                }
                .setNegativeButton(android.R.string.cancel, null)
                .show()
        }, {
            val intent = Intent(this@ListNakesActivity, DataNakesAwalActivity::class.java).apply {
                putExtra("ekohort_android.current", it)
            }
            startActivity(intent)
        }, {
            val intent = Intent(this@ListNakesActivity, DetailNakesActivity::class.java).apply {
                putExtra("ekohort_android.current", it)
            }
            startActivity(intent)
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListIbuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            titleDataPelayananIbu.setText(R.string.data_nakes_pada_puskesmas)
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
                val intent = Intent(this@ListNakesActivity, DataNakesAwalActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun RecyclerView.showRecyclerList() {
        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            this.layoutManager = GridLayoutManager(this@ListNakesActivity, 2)
        } else{
            this.layoutManager = LinearLayoutManager(this@ListNakesActivity)
        }
        this.adapter = this@ListNakesActivity.adapter
    }
}