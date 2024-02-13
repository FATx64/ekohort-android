package com.example.ekohort_android.data.ibu

import com.example.ekohort_android.domain.ibu.IbuRepository
import com.example.ekohort_android.domain.ibu.model.Ibu
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class IbuRepositoryImpl(
    private val db: FirebaseFirestore
) : IbuRepository {

    override suspend fun getAllIbu(): List<Ibu> {
        return db.collection("ibu").get().await().map { document ->
            val rt = document.toObject(Ibu::class.java)
            rt.id = document.id
            rt
        }
    }

    override fun insert() {
        TODO("Not yet implemented")
    }

    override fun delete() {
        TODO("Not yet implemented")
    }
}