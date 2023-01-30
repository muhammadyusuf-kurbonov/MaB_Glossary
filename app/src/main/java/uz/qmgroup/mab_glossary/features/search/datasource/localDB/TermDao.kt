package uz.qmgroup.mab_glossary.features.search.datasource.localDB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TermDao {
    @Insert
    suspend fun insertTerm(term: LocalDBTermEntity)

    @Query("SELECT * FROM LocalDBTermEntity WHERE term LIKE '%' || :query || '%'")
    suspend fun searchTerm(query: String): List<LocalDBTermEntity>
}