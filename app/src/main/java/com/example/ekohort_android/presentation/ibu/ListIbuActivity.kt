package com.example.ekohort_android.presentation.ibu

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ekohort_android.databinding.ActivityListIbuBinding
import com.example.ekohort_android.domain.ibu.model.Ibu
import com.example.ekohort_android.presentation.base.BaseActivity
import com.example.ekohort_android.presentation.ibu.form.DataIbuAwalActivity
import java.util.*

class ListIbuActivity : BaseActivity<ActivityListIbuBinding>() {
    private val dataList = ArrayList<Ibu>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListIbuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            rvItemIbu.setHasFixedSize(true)
            rvItemIbu.showRecyclerList()
            // TODO: Replace dummy data with real data from firebase
            dataList.addAll(listOf(
                Ibu("test", 0L, 0L, Date(), "test", "test", 0, 0, "test", Date(), Date(), "test"),
                Ibu("test2", 0L, 0L, Date(), "test", "test2", 0, 0, "test", Date(), Date(), "test"),
            ))

            btnAddData.setOnClickListener {
                val intent = Intent(this@ListIbuActivity, DataIbuAwalActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun RecyclerView.showRecyclerList() {
        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            this.layoutManager = GridLayoutManager(this@ListIbuActivity, 2)
        } else{
            this.layoutManager = LinearLayoutManager(this@ListIbuActivity)
        }
        val adapter = ListIbuAdapter(dataList)
        this.adapter = adapter
    }
}