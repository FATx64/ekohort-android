package com.example.ekohort_android.data.nakes

import com.example.ekohort_android.domain.nakes.NakesRepository
import com.example.ekohort_android.domain.nakes.model.Nakes
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class NakesRepositoryImpl(
    private val db: FirebaseFirestore
) : NakesRepository {
    override suspend fun getNakesById(id: String): Nakes? {
        return try {
            db.collection(Nakes.COLLECTION_NAME).document(id).get().await().toObject()
        } catch (_: Exception) {
            null
        }
    }

    override fun getNakesByIdAsFlow(id: String): Flow<Nakes?> {
        return db.collection(Nakes.COLLECTION_NAME).document(id).snapshots().map { it.toObject() }
    }

    override suspend fun getAllNakes(): List<Nakes> {
        return db.collection(Nakes.COLLECTION_NAME).get().await().map { it.toObject() }
    }
    
    override fun getAllNakesAsFlow(): Flow<List<Nakes>> {
        return db.collection(Nakes.COLLECTION_NAME).snapshots().map(QuerySnapshot::toObjects)
    }

    override suspend fun insert(data: Nakes) {
        db.collection(Nakes.COLLECTION_NAME).document().set(data).await()
    }

    override suspend fun delete(id: String) {
        db.collection(Nakes.COLLECTION_NAME).document(id).delete().await()
    }

    override suspend fun update(id: String, data: Nakes) {
        getNakesById(id)?.let {
            db.collection(Nakes.COLLECTION_NAME).document(it.id).set(data).await()
        }
    }
}