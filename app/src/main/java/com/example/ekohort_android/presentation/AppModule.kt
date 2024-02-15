package com.example.ekohort_android.presentation

import com.example.ekohort_android.presentation.ibu.ListIbuViewModel
import com.example.ekohort_android.presentation.ibu.detail.DetailIbuViewModel
import com.example.ekohort_android.presentation.ibu.form.DataIbuViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules = module {
    viewModel { (id: String) -> DetailIbuViewModel(get(), id) }
    viewModel { DataIbuViewModel(get()) }
    viewModel { ListIbuViewModel(get()) }
}