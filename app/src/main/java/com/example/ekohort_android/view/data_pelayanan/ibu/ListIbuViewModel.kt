package com.example.ekohort_android.view.data_pelayanan.ibu

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.ekohort_android.R
import com.example.ekohort_android.db.ibuDb
import com.example.ekohort_android.repository.IbuRepository

class ListIbuViewModel (application: Application): ViewModel()  {

    private val mIbuRepository: IbuRepository = IbuRepository(application)

    fun getAllIbuData(): LiveData<List<ibuDb>> = mIbuRepository.getAllIbuData()

}