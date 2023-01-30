package uz.qmgroup.mab_glossary.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uz.qmgroup.mab_glossary.R
import uz.qmgroup.mab_glossary.features.search.models.Term
import uz.qmgroup.mab_glossary.ui.theme.MaBGlossaryTheme

@Composable
fun TermComponent(
    modifier: Modifier = Modifier,
    term: Term,
    buttonText: String = stringResource(R.string.word_detail),
    onButtonClick: () -> Unit = {}
) {
    ElevatedCard(modifier = modifier) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = term.term, style = MaterialTheme.typography.titleLarge)

            Text(
                text = term.definition,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            Button(onClick = onButtonClick) {
                Text(text = buttonText)
            }
        }
    }
}

@Preview
@Composable
fun TermComponentPreview() {
    MaBGlossaryTheme {
        TermComponent(
            modifier = Modifier.fillMaxWidth(),
            term = Term(
                id = 0,
                term = "Bank",
                definition = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse consectetur eleifend sem, vel sagittis massa laoreet nec. Nulla at velit maximus, faucibus neque eget, rhoncus est. Fusce consequat elementum orci eget mollis. Mauris sagittis, odio ut fermentum fringilla, orci augue tristique dolor, in rhoncus risus ligula sed elit. "
            )
        )
    }
}