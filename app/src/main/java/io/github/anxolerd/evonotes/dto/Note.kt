package io.github.anxolerd.evonotes.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.LocalDateTime

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,

    @ColumnInfo(name = "date_created", collate = ColumnInfo.INTEGER)
    val dateCreated: LocalDateTime,

    @ColumnInfo(name = "date_modified", collate = ColumnInfo.INTEGER)
    var dateModified: LocalDateTime,

    var content: String
)