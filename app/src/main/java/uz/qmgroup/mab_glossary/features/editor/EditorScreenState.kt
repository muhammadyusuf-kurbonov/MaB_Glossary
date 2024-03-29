package uz.qmgroup.mab_glossary.features.editor

import uz.qmgroup.mab_glossary.features.search.models.Term

sealed class EditorScreenState {
    object Loading: EditorScreenState()

    data class Synchronising(val currentLoaded: Long, val total: Long): EditorScreenState()

    data class DataFetched(val list: List<Term>): EditorScreenState()

    object NoDataFound: EditorScreenState()
}
