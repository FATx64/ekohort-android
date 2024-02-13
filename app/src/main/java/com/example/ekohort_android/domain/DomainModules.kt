package com.example.ekohort_android.domain

import com.example.ekohort_android.data.ibu.IbuRepositoryImpl
import com.example.ekohort_android.domain.ibu.IbuRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.koin.dsl.module

val domainModules = module {
    single { Firebase.firestore }
    single { Firebase.auth }

    single<IbuRepository> { IbuRepositoryImpl(get()) }
}