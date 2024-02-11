package com.example.ekohort_android.presentation.auth

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.ekohort_android.R
import com.example.ekohort_android.databinding.ActivityRegisterBinding
import com.example.ekohort_android.presentation.base.BaseActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class RegisterActivity : BaseActivity<ActivityRegisterBinding>() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.buttonRegister.setOnClickListener {

            val username = binding.nameEditText.text.toString().trim()
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()

            // Perform Firebase user Registration

            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {task ->
                    if (task.isSuccessful){
                        //showing pop up to login page
                        registrationPopup()

                        firebaseAuth.currentUser?.updateProfile(
                            UserProfileChangeRequest.Builder()
                                .setDisplayName(username)
                                .build()
                        )

                    }else{
                        Toast.makeText(
                            this,
                            "Registration Failed: ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }

        }

    }

    private fun registrationPopup(){
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.custom_pop_up)

        val successImageView = dialog.findViewById<ImageView>(R.id.image_popup)
        val succesHeadingMessageTextView = dialog.findViewById<TextView>(R.id.text_heading_popup)
        val successMessageTextView = dialog.findViewById<TextView>(R.id.text_popup)
        val loginButton = dialog.findViewById<Button>(R.id.button_login_now)

        loginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            //dialog.dismiss()
        }

        dialog.show()

    }




}