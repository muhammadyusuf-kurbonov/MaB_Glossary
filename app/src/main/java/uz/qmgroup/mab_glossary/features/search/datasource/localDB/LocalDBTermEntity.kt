package uz.qmgroup.mab_glossary.features.search.datasource.localDB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocalDBTermEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(index = true)
    val term: String,
    val definition: String
)
