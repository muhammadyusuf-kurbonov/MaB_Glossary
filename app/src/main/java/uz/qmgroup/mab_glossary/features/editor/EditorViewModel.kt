package uz.qmgroup.mab_glossary.features.editor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uz.qmgroup.mab_glossary.features.search.datasource.TermDataSource
import uz.qmgroup.mab_glossary.features.search.models.Term

class EditorViewModel(
    private val source: TermDataSource
) : ViewModel() {
    private val _state = MutableStateFlow<EditorScreenState>(EditorScreenState.Loading)
    val state = _state.asStateFlow()

    fun search(searchQuery: String) {
        viewModelScope.launch {
            _state.update { EditorScreenState.Loading }

            _state.emitAll(source.searchTerms(searchQuery).map { data ->
                if (data.isEmpty())
                    EditorScreenState.NoDataFound
                else
                    EditorScreenState.DataFetched(data)
            })
        }
    }

    suspend fun saveTerm(term: Term) {
        withContext(Dispatchers.IO) {
            if (term.id == null) {
                source.insert(term)
            } else {
                source.update(term)
            }
        }
    }
}