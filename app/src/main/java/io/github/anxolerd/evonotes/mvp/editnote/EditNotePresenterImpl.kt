package io.github.anxolerd.evonotes.mvp.editnote

import io.github.anxolerd.evonotes.dto.Note
import io.github.anxolerd.evonotes.navigation.NavigationManager
import io.github.anxolerd.evonotes.repository.NotesRepository
import org.threeten.bp.LocalDateTime

class EditNotePresenterImpl(
    private val repository: NotesRepository,
    private val navigationManager: NavigationManager
) : EditNotePresenter {
    override fun getNote(noteId: Long): Note {
        return checkNotNull(repository.getNote(noteId))
    }

    override fun saveNote(noteId: Long?, content: String): Note {
        val note = if (noteId == null) {
            val newNote = Note(
                dateCreated = LocalDateTime.now(),
                dateModified = LocalDateTime.now(),
                content = content
            )
            repository.saveNote(newNote)
        } else {
            val existingNote = getNote(noteId)
            existingNote.content = content
            existingNote.dateModified = LocalDateTime.now()
            repository.editNote(existingNote)
        }
        return note
    }

    override fun navigateToNotesList() {
        navigationManager.showNotesList()
    }
}