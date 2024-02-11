package com.example.ekohort_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.ekohort_android.welcome_screen.OnboardingScreen1

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val wic = WindowInsetsControllerCompat(window, window.decorView)
        wic.hide(WindowInsetsCompat.Type.systemBars())

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, OnboardingScreen1::class.java)
            startActivity(intent)
        }, 2000L)
    }

}