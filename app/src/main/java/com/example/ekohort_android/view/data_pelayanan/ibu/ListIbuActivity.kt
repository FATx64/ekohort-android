package com.example.ekohort_android.view.data_pelayanan.ibu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ekohort_android.R
import com.example.ekohort_android.databinding.ActivityListIbuBinding
import com.example.ekohort_android.view.data_pelayanan.ibu.stepperform.DataIbuAwal

class ListIbuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListIbuBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityListIbuBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.apply {
            btnAddData.setOnClickListener {
                val intent = Intent(this@ListIbuActivity, DataIbuAwal::class.java)
                startActivity(intent)
            }
        }
    }
}