package uz.qmgroup.mab_glossary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.alexgladkov.odyssey.compose.extensions.screen
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ru.alexgladkov.odyssey.compose.setup.OdysseyConfiguration
import ru.alexgladkov.odyssey.compose.setup.setNavigationContent
import ru.alexgladkov.odyssey.core.configuration.DisplayType
import uz.qmgroup.mab_glossary.features.editor.EditorScreen
import uz.qmgroup.mab_glossary.features.editor.di.editorKoinModule
import uz.qmgroup.mab_glossary.features.search.SearchScreen
import uz.qmgroup.mab_glossary.features.search.di.searchKoinModule
import uz.qmgroup.mab_glossary.ui.theme.MaBGlossaryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        startKoin {
            androidContext(this@MainActivity.applicationContext)
            modules(searchKoinModule, editorKoinModule)
        }

        setContent {
            MaBGlossaryTheme {
                val configuration = OdysseyConfiguration(
                    canvas = this,
                    displayType = DisplayType.EdgeToEdge,
                    backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                )

                setNavigationContent(configuration) {
                    navigationGraph()
                }
            }
        }
    }
}

fun RootComposeBuilder.navigationGraph(modifier: Modifier = Modifier) {
    this.screen("search") { SearchScreen(modifier = modifier.fillMaxSize().imePadding()) }
    this.screen("editor") { EditorScreen(modifier = modifier.fillMaxSize().imePadding()) }
}