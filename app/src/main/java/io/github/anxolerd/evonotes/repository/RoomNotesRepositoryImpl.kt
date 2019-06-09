package io.github.anxolerd.evonotes.repository

import android.content.Context
import androidx.room.*
import io.github.anxolerd.evonotes.dto.Note
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset


@Dao
interface NotesDAO {
    @Query("SELECT * FROM notes ORDER BY id ASC")
    fun getNotes(): List<Note>

    @Query("SELECT * FROM notes WHERE id = :id")
    fun getNote(id: Long): Note?

    @Insert
    fun saveNote(note: Note): Long

    @Update
    fun editNote(note: Note)

    @Delete
    fun deleteNote(note: Note)
}


class Converter {
    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDateTime? {
        return value?.let {
            LocalDateTime.ofEpochSecond(value, 0, ZoneOffset.UTC)
        }
    }

    @TypeConverter
    fun toTimestamp(value: LocalDateTime): Long {
        return value.toEpochSecond(ZoneOffset.UTC)
    }
}

@Database(version = 1, entities = [Note::class])
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getNotesDAO(): NotesDAO
}


class RoomNotesRepositoryImpl(context: Context) : NotesRepository {
    private val db: AppDatabase by lazy {
        Room.databaseBuilder(context, AppDatabase::class.java, "evonotes-database")
            .build()
    }

    override fun getNotes(): List<Note> {
        return db.getNotesDAO().getNotes()
    }

    override fun getNote(id: Long): Note? {
        return db.getNotesDAO().getNote(id)
    }

    override fun saveNote(note: Note): Note {
        val id = db.getNotesDAO().saveNote(note)
        note.id = id
        return note
    }

    override fun editNote(note: Note): Note {
        db.getNotesDAO().editNote(note)
        return note
    }

    override fun deleteNote(note: Note) {
        db.getNotesDAO().deleteNote(note);
    }


}