package uz.qmgroup.mab_glossary.features.search

import uz.qmgroup.mab_glossary.features.search.models.Term

sealed class SearchScreenState {
    object Loading: SearchScreenState()

    data class DataFetched(val list: List<Term>): SearchScreenState()

    object NoDataFound: SearchScreenState()
}
