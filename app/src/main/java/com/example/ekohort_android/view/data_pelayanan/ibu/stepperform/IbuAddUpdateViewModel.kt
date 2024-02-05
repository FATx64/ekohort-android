package com.example.ekohort_android.view.data_pelayanan.ibu.stepperform

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.example.ekohort_android.R
import com.example.ekohort_android.db.ibuDb
import com.example.ekohort_android.repository.IbuRepository

class IbuAddUpdateViewModel (application: Application): ViewModel() {

    private val mIbuRepository: IbuRepository = IbuRepository(application)

    fun insert(ibu: ibuDb){
        mIbuRepository.insert(ibu)
    }

    fun update (ibu: ibuDb){
        mIbuRepository.update(ibu)
    }

    fun delete (ibu: ibuDb){
        mIbuRepository.delete(ibu)
    }
}