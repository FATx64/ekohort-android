package com.example.ekohort_android

import android.app.Application
import com.example.ekohort_android.domain.domainModules
import com.example.ekohort_android.presentation.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class EkohortApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@EkohortApplication)
            // Load modules
            modules(appModules, domainModules)
        }
    }
}