package com.example.ekohort_android.presentation.base

import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB: ViewBinding> : AppCompatActivity() {
    lateinit var binding: VB
    val isBindingInitialized get() = this::binding.isInitialized
}