package uz.qmgroup.mab_glossary.features.search.datasource

import kotlinx.coroutines.flow.Flow
import uz.qmgroup.mab_glossary.features.search.models.Term

interface TermDataSource {
    suspend fun searchTerms(query: String): Flow<List<Term>>

    suspend fun insert(term: Term)

    suspend fun update(term: Term)

    suspend fun insertOrUpdate(term: Term)

    fun loadPaged(): Flow<Term>
}