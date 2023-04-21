package uz.qmgroup.mab_glossary.features.search.datasource

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import uz.qmgroup.mab_glossary.features.search.datasource.localDB.TermDatabase
import uz.qmgroup.mab_glossary.features.search.datasource.localDB.toEntity
import uz.qmgroup.mab_glossary.features.search.datasource.localDB.toTerm
import uz.qmgroup.mab_glossary.features.search.models.Term

class TermDataSourceImpl(private val database: TermDatabase) : TermDataSource {
    override suspend fun searchTerms(query: String): Flow<List<Term>> {
        return database.termDao.searchTerm(query).map { it.map { entity -> entity.toTerm() } }
    }

    override suspend fun insert(term: Term) {
        database.termDao.insertTerm(term = term.toEntity())
    }

    override suspend fun update(term: Term) {
        database.termDao.updateTerm(term = term.toEntity())
    }

    override suspend fun insertOrUpdate(term: Term) = withContext(Dispatchers.IO) {
        database.termDao.upsertTerm(term.toEntity())
    }

    override fun loadPaged(): Flow<Term> = flow {
        var offset = 0
        do {
            val data = database.termDao.loadBatch(offset).map { it.toTerm() }
            data.forEach { emit(it) }
            offset += 5
        } while (data.isNotEmpty())
    }
}