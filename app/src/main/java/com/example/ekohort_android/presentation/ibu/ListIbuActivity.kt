package com.example.ekohort_android.presentation.ibu

import android.content.Intent
import android.os.Bundle
import com.example.ekohort_android.databinding.ActivityListIbuBinding
import com.example.ekohort_android.presentation.base.BaseActivity
import com.example.ekohort_android.presentation.ibu.form.DataIbuAwalActivity

class ListIbuActivity : BaseActivity<ActivityListIbuBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListIbuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnAddData.setOnClickListener {
                val intent = Intent(this@ListIbuActivity, DataIbuAwalActivity::class.java)
                startActivity(intent)
            }
        }
    }
}