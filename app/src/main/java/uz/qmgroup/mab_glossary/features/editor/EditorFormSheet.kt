package uz.qmgroup.mab_glossary.features.editor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import uz.qmgroup.mab_glossary.R
import uz.qmgroup.mab_glossary.features.search.models.Term

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditorFormSheet(
    modifier: Modifier = Modifier,
    closeRequest: () -> Unit,
    termObject: Term? = null,
    saveAction: suspend (Term) -> Unit
) {
    val scope = rememberCoroutineScope()
    var loading by remember {
        mutableStateOf(false)
    }
    var term by remember {
        mutableStateOf("")
    }

    var definition by remember {
        mutableStateOf("")
    }

    LaunchedEffect(key1 = termObject) {
        if (termObject == null) return@LaunchedEffect

        term = termObject.term
        definition = termObject.definition
    }

    Surface(modifier = modifier) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = "New term", style = MaterialTheme.typography.titleMedium)
            OutlinedTextField(
                value = term,
                onValueChange = { term = it },
                label = { Text(text = stringResource(R.string.term)) }
            )
            OutlinedTextField(
                value = definition,
                onValueChange = { definition = it },
                label = { Text(text = stringResource(R.string.Definition)) },
                singleLine = false,
                maxLines = 3,
                modifier = Modifier.heightIn(min = 128.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.align(Alignment.End)) {
                TextButton(onClick = closeRequest) {
                    Text(text = stringResource(R.string.Cancel))
                }

                Button(
                    onClick = {
                        scope.launch {
                            loading = true
                            saveAction(
                                termObject?.copy(term = term, definition = definition)
                                    ?: Term(id = null, term, definition)
                            )
                            closeRequest()
                        }
                    },
                    enabled = !loading
                ) {
                    Text(text = stringResource(R.string.Save))
                }
            }
        }
    }
}