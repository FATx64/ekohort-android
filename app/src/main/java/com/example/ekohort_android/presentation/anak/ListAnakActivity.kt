package com.example.ekohort_android.presentation.anak

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.SearchView
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
import com.example.ekohort_android.domain.anak.model.Anak
import com.example.ekohort_android.presentation.anak.detail.DetailAnakActivity
import com.example.ekohort_android.presentation.anak.form.DataAnakAwalActivity
import com.example.ekohort_android.presentation.base.BaseActivity
import com.example.ekohort_android.presentation.ibu.ListIbuAdapter
import com.example.ekohort_android.utils.exts.toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListAnakActivity : BaseActivity<ActivityListIbuBinding>() {
    private val viewModel: ListAnakViewModel by viewModel()
    private val adapter by lazy {
        ListIbuAdapter<Anak, Anak>({
            MaterialAlertDialogBuilder(this@ListAnakActivity)
                .setMessage("Are you sure?")
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    viewModel.delete(it)
                }
                .setNegativeButton(android.R.string.cancel, null)
                .show()
        }, {
            val intent = Intent(this@ListAnakActivity, DataAnakAwalActivity::class.java).apply {
                putExtra("ekohort_android.current", it)
            }
            startActivity(intent)
        }, {
            val intent = Intent(this@ListAnakActivity, DetailAnakActivity::class.java).apply {
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
            titleDataPelayananIbu.setText(R.string.data_pelayanan_anak)
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
                val intent = Intent(this@ListAnakActivity, DataAnakAwalActivity::class.java)
                startActivity(intent)
            }

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    viewModel.search(query.orEmpty())
                    return false
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    return false
                }
            })

            val closeBtn: View = searchView.findViewById(androidx.appcompat.R.id.search_close_btn)
            closeBtn.setOnClickListener {
                searchView.setQuery("", false)
                searchView.isIconified = true
            }

            val searchPlate: EditText = searchView.findViewById(androidx.appcompat.R.id.search_src_text)
            searchPlate.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    viewModel.search(searchView.query.toString())
                }
                false
            }
        }
    }

    private fun RecyclerView.showRecyclerList() {
        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            this.layoutManager = GridLayoutManager(this@ListAnakActivity, 2)
        } else{
            this.layoutManager = LinearLayoutManager(this@ListAnakActivity)
        }
        this.adapter = this@ListAnakActivity.adapter
    }
}