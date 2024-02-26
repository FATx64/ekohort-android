package com.example.ekohort_android.domain

import com.example.ekohort_android.data.CarouselRepositoryImpl
import com.example.ekohort_android.data.anak.AnakRepositoryImpl
import com.example.ekohort_android.data.ibu.IbuRepositoryImpl
import com.example.ekohort_android.data.nakes.NakesRepositoryImpl
import com.example.ekohort_android.domain.anak.AnakRepository
import com.example.ekohort_android.domain.carousel.CarouselRepository
import com.example.ekohort_android.domain.ibu.IbuRepository
import com.example.ekohort_android.domain.nakes.NakesRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.koin.dsl.module

val domainModules = module {
    single { Firebase.firestore }
    single { Firebase.auth }

    single<AnakRepository> { AnakRepositoryImpl(get()) }
    single<IbuRepository> { IbuRepositoryImpl(get()) }
    single<NakesRepository> { NakesRepositoryImpl(get()) }
    single<CarouselRepository> { CarouselRepositoryImpl(get()) }
}