package com.example.ekohort_android.welcome_screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ekohort_android.MainActivity
import com.example.ekohort_android.R
import com.example.ekohort_android.auth.LoginActivity
import com.example.ekohort_android.databinding.ActivityOnboardingScreen1Binding
import com.example.ekohort_android.databinding.ActivityOnboardingScreen2Binding

class OnboardingScreen2 : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingScreen2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingScreen2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnGetStarted.setOnClickListener {
                val intent = Intent(this@OnboardingScreen2, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }

}