package io.github.anxolerd.evonotes.repository

import io.github.anxolerd.evonotes.dto.Note

interface NotesRepository {
    fun getNotes(): List<Note>
    fun getNote(id: Long): Note?
    fun saveNote(note: Note): Note
    fun editNote(note: Note): Note
    fun deleteNote(note: Note)
}