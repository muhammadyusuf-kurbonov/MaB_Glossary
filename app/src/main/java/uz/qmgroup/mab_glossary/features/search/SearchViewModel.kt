package uz.qmgroup.mab_glossary.features.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.qmgroup.mab_glossary.features.search.datasource.TermDataSource
import uz.qmgroup.mab_glossary.features.search.datasource.localDB.LocalDBTermEntity
import uz.qmgroup.mab_glossary.features.search.datasource.localDB.TermDatabase

class SearchViewModel(private val source: TermDataSource, private val database: TermDatabase) :
    ViewModel() {
    private val _state = MutableStateFlow<SearchScreenState>(SearchScreenState.Loading)
    val state = _state.asStateFlow()

    fun search(searchQuery: String) {
        viewModelScope.launch {
            _state.update { SearchScreenState.Loading }

            val data = source.searchTerms(searchQuery)

            _state.update {
                if (data.isEmpty())
                    SearchScreenState.NoDataFound
                else
                    SearchScreenState.DataFetched(data)
            }
        }
    }

    fun insertNew() {
        viewModelScope.launch {
            database.termDao.insertTerm(
                LocalDBTermEntity(
                    id = 0,
                    term = "Bank",
                    definition = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse consectetur eleifend sem, vel sagittis massa laoreet nec. Nulla at velit maximus, faucibus neque eget, rhoncus est. Fusce consequat elementum orci eget mollis. Mauris sagittis, odio ut fermentum fringilla, orci augue tristique dolor, in rhoncus risus ligula sed elit. "
                )
            )

            search("")
        }
    }
}