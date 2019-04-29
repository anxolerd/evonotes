package io.github.anxolerd.evonotes.repository

import io.github.anxolerd.evonotes.dto.Note
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import kotlin.collections.ArrayList

class InMemoryNotesRepositoryImpl: NotesRepository {
    companion object {
        private var id: Long = 1;
        private fun nextId(): Long = id++
    }

    private val notes: MutableList<Note> = ArrayList();

    // TODO: delete
    init {
        // fake data for testing
        (1..5).forEach {
            val id = nextId()
            notes.add(Note(id, dateCreated = LocalDateTime.now(), dateModified = LocalDateTime.now(), content = "Note #$id"))
        }
    }

    override fun getNotes(): List<Note> {
        return this.notes
    }

    override fun getNote(id: Long): Note? {
        return this.notes.find { it.id == id }
    }

    override fun saveNote(note: Note): Note {
        note.id = nextId()
        notes.add(note)
        return note
    }

    override fun editNote(note: Note): Note {
        return note
    }
}