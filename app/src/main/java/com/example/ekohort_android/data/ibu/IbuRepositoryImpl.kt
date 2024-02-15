package com.example.ekohort_android.data.ibu

import com.example.ekohort_android.domain.ibu.IbuRepository
import com.example.ekohort_android.domain.ibu.model.Ibu
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class IbuRepositoryImpl(
    private val db: FirebaseFirestore
) : IbuRepository {
    override suspend fun getIbuById(id: String): Ibu? {
        return try {
            db.collection("ibu").document(id).get().await().toObject(Ibu::class.java)
        } catch (_: Exception) {
            null
        }
    }

    override suspend fun getAllIbu(): List<Ibu> {
        return db.collection("ibu").get().await().map { it.toObject(Ibu::class.java) }
    }

    override suspend fun insert(data: Ibu) {
        db.collection("ibu").document().set(data).await()
    }

    override suspend fun delete(id: String) {
        db.collection("ibu").document(id).delete().await()
    }

    override suspend fun update(id: String, data: Ibu) {
        getIbuById(id)?.let {
            db.collection("ibu").document(it.id).set(data).await()
        }
    }
}