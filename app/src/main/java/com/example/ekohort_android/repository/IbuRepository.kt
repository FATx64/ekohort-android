package com.example.ekohort_android.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.ekohort_android.db.IbuDao
import com.example.ekohort_android.db.IbuRoomDatabase
import com.example.ekohort_android.db.ibuDb
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class IbuRepository (application: Application) {

    private val mIbuDao: IbuDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = IbuRoomDatabase.getDatabase(application)
        mIbuDao = db.ibuDao()
    }

    fun getAllIbuData(): LiveData<List<ibuDb>> = mIbuDao.getAllIbuData()

    fun insert(ibu: ibuDb){
        executorService.execute { mIbuDao.insert(ibu) }
    }

    fun delete(ibu: ibuDb){
        executorService.execute { mIbuDao.delete(ibu) }
    }

    fun update(ibu: ibuDb){
        executorService.execute { mIbuDao.update(ibu) }
    }

}