package uz.qmgroup.mab_glossary.features.editor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.qmgroup.mab_glossary.features.search.datasource.TermDataSource

class EditorViewModel(private val source: TermDataSource) :
    ViewModel() {
    private val _state = MutableStateFlow<EditorScreenState>(EditorScreenState.Loading)
    val state = _state.asStateFlow()

    fun search(searchQuery: String) {
        viewModelScope.launch {
            _state.update { EditorScreenState.Loading }

            val data = source.searchTerms(searchQuery)

            _state.update {
                if (data.isEmpty())
                    EditorScreenState.NoDataFound
                else
                    EditorScreenState.DataFetched(data)
            }
        }
    }
}