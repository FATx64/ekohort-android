package com.example.ekohort_android.data.ibu

import com.example.ekohort_android.domain.ibu.IbuRepository
import com.example.ekohort_android.domain.ibu.model.Ibu
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class IbuRepositoryImpl(
    private val db: FirebaseFirestore
) : IbuRepository {
    override suspend fun getIbuById(id: String): Ibu? {
        return try {
            db.collection("ibu").document(id).get().await().toObject()
        } catch (_: Exception) {
            null
        }
    }

    override suspend fun getAllIbu(): List<Ibu> {
        return db.collection("ibu").get().await().map { it.toObject() }
    }
    
    override fun getAllIbuAsFlow(): Flow<List<Ibu>> {
        return db.collection("ibu").snapshots().map(QuerySnapshot::toObjects)
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