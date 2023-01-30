package uz.qmgroup.mab_glossary.features.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel
import uz.qmgroup.mab_glossary.R
import uz.qmgroup.mab_glossary.features.search.components.TermComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = koinViewModel()
) {
    var searchQuery by remember {
        mutableStateOf("")
    }

    LaunchedEffect(key1 = viewModel, key2 = searchQuery) {
        delay(200)
        viewModel.search(searchQuery)
    }

    val currentState by viewModel.state.collectAsState()
    val state = currentState
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(title = {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 16.dp),
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = {
                        Text(text = stringResource(R.string.search_in_glossary))
                    },
                    shape = RoundedCornerShape(99.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent,
                    ),
                    textStyle = MaterialTheme.typography.bodyLarge,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = stringResource(id = R.string.search_in_glossary),
                            modifier = Modifier.padding(16.dp)
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = viewModel::insertNew) {
                            Icon(imageVector = Icons.Default.AddCircle, contentDescription = "Add")
                        }
                    }
                )
            })
        }
    ) {
        AnimatedVisibility(
            visible = state == SearchScreenState.Loading,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(modifier = modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    CircularProgressIndicator()

                    Text(text = stringResource(R.string.loading), style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
        AnimatedVisibility(
            visible = state is SearchScreenState.NoDataFound,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(modifier = modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = stringResource(R.string.no_data_found), style = MaterialTheme.typography.bodyLarge)
                }
            }
        }

        if (state is SearchScreenState.DataFetched) {
            LazyColumn(
                modifier = Modifier.padding(it),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(state.list) { term ->
                    TermComponent(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        term = term
                    )
                }
            }
        }
    }
}