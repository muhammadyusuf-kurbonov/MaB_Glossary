package uz.qmgroup.mab_glossary.features.search.datasource

import uz.qmgroup.mab_glossary.features.search.datasource.localDB.TermDatabase
import uz.qmgroup.mab_glossary.features.search.datasource.localDB.toTerm
import uz.qmgroup.mab_glossary.features.search.models.Term

class TermDataSourceImpl(private val database: TermDatabase) : TermDataSource {
    override suspend fun searchTerms(query: String): List<Term> {
        return database.termDao.searchTerm(query).map { it.toTerm() }
    }
}