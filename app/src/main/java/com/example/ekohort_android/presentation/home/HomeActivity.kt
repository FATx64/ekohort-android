package com.example.ekohort_android.presentation.home

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.ekohort_android.R
import com.example.ekohort_android.presentation.blog.BlogAdapter
import com.example.ekohort_android.presentation.auth.LoginActivity
import com.example.ekohort_android.databinding.ActivityHomeBinding
import com.example.ekohort_android.domain.blog.model.BlogModel
import com.example.ekohort_android.presentation.anak.ListAnakActivity
import com.example.ekohort_android.presentation.base.BaseActivity
import com.example.ekohort_android.utils.DateUtils
import com.example.ekohort_android.presentation.ibu.ListIbuActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.android.inject

class HomeActivity : BaseActivity<ActivityHomeBinding>() {
    private val googleSigningClient: GoogleSignInClient by lazy {
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("550281026161-ld1lg5ajcd8pjq1t863ue13p11tdrep1.apps.googleusercontent.com")
            .requestEmail()
            .build()

        GoogleSignIn.getClient(this, gso)
    }

    private val auth: FirebaseAuth by inject()

    private val dateUtils = DateUtils()

    private val list = ArrayList<BlogModel>()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            logoutButton.setOnClickListener {
                signOut()
            }
        }

        loginWithGoogle()
        showUserName()
        carouselAdapter()
        menuOnCard()

        binding.rvBlog.setHasFixedSize(true)
        binding.rvBlog.showRecyclerList()
        list.addAll(blogList)

    }

    private fun loginWithGoogle(){
        val textView = findViewById<TextView>(R.id.name)

        val user = auth.currentUser

        //showing username
        if (user != null){
            val userName = user.displayName
            textView.text =  userName
        } else{
            //do nothing
        }
    }

    //daftar menu yang di card
    private fun menuOnCard(){
        binding.layoutMenu.apply {
            icAnak.setOnClickListener {
                val intent = Intent(this@HomeActivity, ListAnakActivity::class.java)
                startActivity(intent)
            }
            icIbu.setOnClickListener {
                val intent = Intent(this@HomeActivity, ListIbuActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun carouselAdapter(){
        val viewPager = findViewById<ViewPager2>(R.id.viewPager2)

        viewPager.apply {
            clipChildren = false
            clipToPadding = false
            offscreenPageLimit = 3
            (getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }

        val demoData = arrayListOf(
            "Data ibu : Jumlah Data",
            "Data anak : Jumlah data",
            "Data lansia: Jumlah Data"
        )
        viewPager.adapter = CarouselAdapter(demoData)

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer((40 * Resources.getSystem().displayMetrics.density).toInt()))
        viewPager.setPageTransformer(compositePageTransformer)
    }

    private fun RecyclerView.showRecyclerList() {
        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            this.layoutManager = GridLayoutManager(this@HomeActivity, 2)
        } else{
            this.layoutManager = LinearLayoutManager(this@HomeActivity)
        }
        val blogAdapter = BlogAdapter(list)
        this.adapter = blogAdapter
    }

    private val blogList: ArrayList<BlogModel>
        get() {
            val dataTitle = resources.getStringArray(R.array.title_blog)
            val dataDescription = resources.getStringArray(R.array.description_blog)
            val dataPhoto = resources.getStringArray(R.array.photo_blog)
            val dataDate = resources.getStringArray(R.array.date_blog)
            val listBlog = ArrayList<BlogModel>()
            for (i in dataTitle.indices){
                val blog = BlogModel(dataTitle[i], dataDescription[i], dataPhoto[i],dataDate[i])
                listBlog.add(blog)
            }
            return listBlog
        }

    private fun showUserName(){
        val currentDate = dateUtils.getCurrentDate()
        binding.dateTextView.text = currentDate
    }

    private fun signOut(){
        auth.signOut()

        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}