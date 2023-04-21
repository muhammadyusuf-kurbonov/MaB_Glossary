package uz.qmgroup.mab_glossary.features.synchronize

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.withContext
import uz.qmgroup.mab_glossary.features.search.datasource.TermDataSource

class Synchronizer(
    private val remote: TermDataSource,
    private val local: TermDataSource
) {
    suspend fun synchronize() {
        Log.d("MyGlossary::Synchronizer", "synchronize: Start upload")
        upstreamSynchronize()
        Log.d("MyGlossary::Synchronizer", "synchronize: Start download")
        downstreamSynchronize()
    }

    private suspend fun downstreamSynchronize() = withContext(Dispatchers.IO) {
        remote.loadPaged().buffer(32).collect {
            local.insertOrUpdate(it)
            Log.d("MyGlossary::Synchronizer", "Downloaded $it")
        }
    }

    private suspend fun upstreamSynchronize() = withContext(Dispatchers.IO) {
        local.loadPaged().buffer(32).collect {
            remote.insertOrUpdate(it)
            Log.d("MyGlossary::Synchronizer", "Uploaded $it")
        }
    }
}