package com.example.ekohort_android.presentation.blog

import android.os.Bundle
import com.example.ekohort_android.databinding.ActivityBlogBinding
import com.example.ekohort_android.presentation.base.BaseActivity

class BlogActivity : BaseActivity<ActivityBlogBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBlogBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}