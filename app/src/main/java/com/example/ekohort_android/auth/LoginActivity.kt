package com.example.ekohort_android.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.core.content.ContextCompat
import com.example.ekohort_android.R
import com.example.ekohort_android.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnLinkRegister.setOnClickListener {
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intent)
            }
        }


    }

    fun hiddenBtn(){

    }
}