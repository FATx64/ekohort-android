package com.example.ekohort_android.presentation.onboarding

import android.content.Intent
import android.os.Bundle
import com.example.ekohort_android.presentation.auth.LoginActivity
import com.example.ekohort_android.databinding.ActivityOnboardingScreen2Binding
import com.example.ekohort_android.presentation.base.BaseActivity

class OnboardingScreen2 : BaseActivity<ActivityOnboardingScreen2Binding>() {

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