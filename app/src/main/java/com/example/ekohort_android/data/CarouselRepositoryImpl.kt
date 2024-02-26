package com.example.ekohort_android.data

import com.example.ekohort_android.domain.carousel.CarouselRepository
import com.example.ekohort_android.domain.anak.model.Anak
import com.example.ekohort_android.domain.ibu.model.Ibu
import com.example.ekohort_android.domain.nakes.model.Nakes
import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class CarouselRepositoryImpl(
    private val db: FirebaseFirestore
) : CarouselRepository {
    override suspend fun getAnakCount(): Int {
        return db.collection(Anak.COLLECTION_NAME).count().get(AggregateSource.SERVER).await().count.toInt()
    }

    override suspend fun getIbuCount(): Int {
        return db.collection(Ibu.COLLECTION_NAME).count().get(AggregateSource.SERVER).await().count.toInt()
    }

    override suspend fun getNakesCount(): Int {
        return db.collection(Nakes.COLLECTION_NAME).count().get(AggregateSource.SERVER).await().count.toInt()
    }
}