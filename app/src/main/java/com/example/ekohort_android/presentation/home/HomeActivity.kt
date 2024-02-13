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
import com.example.ekohort_android.presentation.base.BaseActivity
import com.example.ekohort_android.utils.DateUtils
import com.example.ekohort_android.presentation.ibu.ListIbuActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    private lateinit var googleSigningClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth

    private val dateUtils = DateUtils()

    private lateinit var rvBlog: RecyclerView
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

        rvBlog = findViewById(R.id.rv_blog)
        rvBlog.setHasFixedSize(true)
        shoRecyclerList()
        list.addAll(blogList)

    }

    private fun loginWithGoogle(){
        auth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("550281026161-ld1lg5ajcd8pjq1t863ue13p11tdrep1.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSigningClient = GoogleSignIn.getClient(this, gso)


        googleSigningClient

        val textView = findViewById<TextView>(R.id.name)

        val user = auth.currentUser
        val auth = Firebase.auth

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

        val icDataIbu = binding.layoutMenu.icIbu
        icDataIbu.setOnClickListener {
            val intent = Intent(this, ListIbuActivity::class.java)
            startActivity(intent)
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

    private fun shoRecyclerList(){
        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            rvBlog.layoutManager = GridLayoutManager(this, 2)
        } else{
            rvBlog.layoutManager = LinearLayoutManager(this)
        }
        val blogAdapter = BlogAdapter(list)
        rvBlog.adapter = blogAdapter
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