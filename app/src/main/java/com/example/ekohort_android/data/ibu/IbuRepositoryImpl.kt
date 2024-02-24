package com.example.ekohort_android.data.ibu

import com.example.ekohort_android.domain.ibu.IbuRepository
import com.example.ekohort_android.domain.ibu.model.Ibu
import com.google.firebase.firestore.Filter
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
            db.collection(Ibu.COLLECTION_NAME).document(id).get().await().toObject()
        } catch (_: Exception) {
            null
        }
    }

    override fun getIbuByIdAsFlow(id: String): Flow<Ibu?> {
        return db.collection(Ibu.COLLECTION_NAME).document(id).snapshots().map { it.toObject() }
    }

    override suspend fun getAllIbu(): List<Ibu> {
        return db.collection(Ibu.COLLECTION_NAME).get().await().map { it.toObject() }
    }
    
    override fun getAllIbuAsFlow(searchQuery: String): Flow<List<Ibu>> {
        return db.collection(Ibu.COLLECTION_NAME)
            .where(
                Filter.or(
                    Filter.and(
                        Filter.greaterThanOrEqualTo("name", searchQuery),
                        Filter.lessThanOrEqualTo("name", searchQuery + "\uf8ff")
                    ),
                    Filter.and(
                        Filter.greaterThanOrEqualTo(
                            "name",
                            if (searchQuery.isNotEmpty()) searchQuery[0].uppercase() +
                                    (if (searchQuery.length >= 2) searchQuery.substring(1) else "")
                            else ""
                        ),
                        Filter.lessThanOrEqualTo(
                            "name",
                            (if (searchQuery.isNotEmpty()) searchQuery[0].uppercase() +
                                    (if (searchQuery.length >= 2) searchQuery.substring(1) else "")
                            else "")
                                    + "\uf8ff"
                        )
                    ),
                    Filter.and(
                        Filter.greaterThanOrEqualTo("name", searchQuery.uppercase()),
                        Filter.lessThanOrEqualTo("name", searchQuery.uppercase() + "\uf8ff")
                    ),
                )
            )
            .snapshots()
            .map(QuerySnapshot::toObjects)
    }

    override suspend fun insert(data: Ibu) {
        db.collection(Ibu.COLLECTION_NAME).document().set(data).await()
    }

    override suspend fun delete(id: String) {
        db.collection(Ibu.COLLECTION_NAME).document(id).delete().await()
    }

    override suspend fun update(id: String, data: Ibu) {
        getIbuById(id)?.let {
            db.collection(Ibu.COLLECTION_NAME).document(it.id).set(data).await()
        }
    }
}