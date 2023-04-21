package uz.qmgroup.mab_glossary.features.synchronize.remote

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import uz.qmgroup.mab_glossary.features.search.datasource.TermDataSource
import uz.qmgroup.mab_glossary.features.search.models.Term

class FirestoreTermDataSource : TermDataSource {
    private val collection by lazy {
        Firebase.firestore.collection(COLLECTION_NAME)
    }

    companion object {
        private const val COLLECTION_NAME = "terms"
    }

    override suspend fun searchTerms(query: String): Flow<List<Term>> {
        throw IllegalAccessError("Search directly on remote is not permitted!")
    }

    override suspend fun insert(term: Term) {
        val entity = term.toRemoteEntity()
        collection.document(entity.id.toString()).set(term)
            .await()
    }

    override suspend fun update(term: Term) {
        val entity = term.toRemoteEntity()
        Firebase.firestore.collection(COLLECTION_NAME).document(entity.id.toString()).set(term)
            .await()
    }

    override suspend fun insertOrUpdate(term: Term) {
        val entity = term.toRemoteEntity()
        Firebase.firestore.collection(COLLECTION_NAME).document(entity.id.toString()).set(term)
            .await()
    }

    override fun loadPaged(): Flow<Term> {
        val query = collection.orderBy("id")
        return flow<FirebaseTermEntity> {
            var snapshot = query.get().await()
            var documentChanges = snapshot.documentChanges
            while (documentChanges.isNotEmpty()) {
                for (change in documentChanges) {
                    emit(change.document.toObject())
                }
                val lastDocumentSnapshot = snapshot.documents.lastOrNull()
                snapshot = query.startAfter(lastDocumentSnapshot).get().await()
                documentChanges = snapshot.documentChanges
            }
        }.map { it.toDomain() }.flowOn(Dispatchers.IO)
    }
}