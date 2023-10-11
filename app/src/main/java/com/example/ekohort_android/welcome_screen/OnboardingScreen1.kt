package com.example.ekohort_android.welcome_screen

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.ekohort_android.MainActivity
import com.example.ekohort_android.R
import com.example.ekohort_android.auth.LoginActivity
import com.example.ekohort_android.databinding.ActivityOnboardingScreen1Binding

class OnboardingScreen1 : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingScreen1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingScreen1Binding.inflate(layoutInflater)
        setContentView(binding.root)

       binding.apply {
           btnContinue.setOnClickListener {
               val intent = Intent (this@OnboardingScreen1, OnboardingScreen2::class.java)
               startActivity(intent)
           }
           btnGetStarted.setOnClickListener {
               val intent = Intent(this@OnboardingScreen1, LoginActivity::class.java)
               startActivity(intent)
           }
       }

    }



}