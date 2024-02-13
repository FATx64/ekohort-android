package com.example.ekohort_android.presentation.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.ekohort_android.presentation.home.HomeActivity
import com.example.ekohort_android.presentation.onboarding.OnboardingScreen1
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.android.inject

class MainActivity : Activity() {
    private val auth: FirebaseAuth by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = if (savedInstanceState == null) installSplashScreen() else null

        super.onCreate(savedInstanceState)

        val startTime = System.currentTimeMillis()
        splashScreen?.setKeepOnScreenCondition {
            val elapsed = System.currentTimeMillis() - startTime
            elapsed <= 2000L
        }

        val intent = Intent(
            this,
            if (auth.currentUser == null) OnboardingScreen1::class.java else HomeActivity::class.java
        )
        startActivity(intent)
        finish()
    }
}