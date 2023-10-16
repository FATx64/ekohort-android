package com.example.ekohort_android.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.ekohort_android.R
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }

    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun registerUser (email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                task -> if (task.isSuccessful){
                    val user = auth.currentUser
            }else{
                Log.w("Registration","createUserWithEmail:failure" , task.exception)
            }
            }
    }
}