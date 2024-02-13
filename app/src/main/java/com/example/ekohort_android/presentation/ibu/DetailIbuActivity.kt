package com.example.ekohort_android.presentation.ibu

import android.os.Bundle
import com.example.ekohort_android.databinding.ActivityDetailIbuBinding
import com.example.ekohort_android.presentation.base.BaseActivity

class DetailIbuActivity : BaseActivity<ActivityDetailIbuBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailIbuBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}