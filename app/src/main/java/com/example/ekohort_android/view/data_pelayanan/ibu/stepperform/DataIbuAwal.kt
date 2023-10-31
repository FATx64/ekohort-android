package com.example.ekohort_android.view.data_pelayanan.ibu.stepperform

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.ekohort_android.R
import com.example.ekohort_android.databinding.ActivityDataIbuAwalBinding

class DataIbuAwal : AppCompatActivity() {

    private lateinit var binding: ActivityDataIbuAwalBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityDataIbuAwalBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


    }
}