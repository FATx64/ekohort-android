package com.example.ekohort_android.presentation.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.ekohort_android.presentation.onboarding.OnboardingScreen1

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = if (savedInstanceState == null) installSplashScreen() else null

        super.onCreate(savedInstanceState)

        val startTime = System.currentTimeMillis()
        splashScreen?.setKeepOnScreenCondition {
            val elapsed = System.currentTimeMillis() - startTime
            elapsed <= 2000L
        }

        val intent = Intent(this, OnboardingScreen1::class.java)
        startActivity(intent)
        finish()
    }
}