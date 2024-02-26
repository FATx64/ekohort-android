package com.example.ekohort_android.presentation

import com.example.ekohort_android.presentation.anak.ListAnakViewModel
import com.example.ekohort_android.presentation.anak.detail.DetailAnakViewModel
import com.example.ekohort_android.presentation.anak.form.DataAnakViewModel
import com.example.ekohort_android.presentation.home.HomeViewModel
import com.example.ekohort_android.presentation.ibu.ListIbuViewModel
import com.example.ekohort_android.presentation.ibu.detail.DetailIbuViewModel
import com.example.ekohort_android.presentation.ibu.form.DataIbuViewModel
import com.example.ekohort_android.presentation.nakes.ListNakesViewModel
import com.example.ekohort_android.presentation.nakes.detail.DetailNakesViewModel
import com.example.ekohort_android.presentation.nakes.form.DataNakesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules = module {
    viewModel { params -> DetailIbuViewModel(params.get(), params.get()) }
    viewModel { DataIbuViewModel(get()) }
    viewModel { ListIbuViewModel(get()) }

    viewModel { params -> DetailAnakViewModel(params.get(), params.get()) }
    viewModel { DataAnakViewModel(get()) }
    viewModel { ListAnakViewModel(get()) }

    viewModel { params -> DetailNakesViewModel(params.get(), params.get()) }
    viewModel { DataNakesViewModel(get()) }
    viewModel { ListNakesViewModel(get()) }

    viewModel { HomeViewModel(get()) }
}