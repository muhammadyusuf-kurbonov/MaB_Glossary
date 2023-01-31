package uz.qmgroup.mab_glossary.features.search.datasource.localDB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TermDao {
    @Insert
    suspend fun insertTerm(term: LocalDBTermEntity)

    @Query("SELECT * FROM LocalDBTermEntity WHERE term LIKE '%' || :query || '%'")
    fun searchTerm(query: String): Flow<List<LocalDBTermEntity>>

    @Update
    suspend fun updateTerm(term: LocalDBTermEntity)
}