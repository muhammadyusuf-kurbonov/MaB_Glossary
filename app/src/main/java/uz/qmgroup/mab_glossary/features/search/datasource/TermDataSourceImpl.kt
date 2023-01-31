package uz.qmgroup.mab_glossary.features.search.datasource

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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
}