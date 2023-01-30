package uz.qmgroup.mab_glossary.features.search.datasource

import uz.qmgroup.mab_glossary.features.search.models.Term

interface TermDataSource {
    suspend fun searchTerms(query: String): List<Term>
}