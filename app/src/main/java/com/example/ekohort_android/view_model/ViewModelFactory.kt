package com.example.ekohort_android.view_model

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ekohort_android.view.data_pelayanan.ibu.ListIbuViewModel
import com.example.ekohort_android.view.data_pelayanan.ibu.stepperform.IbuAddUpdateViewModel

class ViewModelFactory private constructor(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory(){

    companion object{
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application) : ViewModelFactory{
            if (INSTANCE ==null){
                synchronized(ViewModelFactory::class.java){
                    INSTANCE = ViewModelFactory(application)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListIbuViewModel::class.java)){
            return ListIbuViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(IbuAddUpdateViewModel::class.java)){
            return IbuAddUpdateViewModel(mApplication) as T
        }
        throw IllegalAccessException("Unknown ViewModel class : ${modelClass.name}")
    }
}