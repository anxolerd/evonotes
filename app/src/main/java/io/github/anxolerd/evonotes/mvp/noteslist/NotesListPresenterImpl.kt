package io.github.anxolerd.evonotes.mvp.noteslist

import io.github.anxolerd.evonotes.dto.Note
import io.github.anxolerd.evonotes.navigation.NavigationManager
import io.github.anxolerd.evonotes.repository.NotesRepository

class NotesListPresenterImpl(
    val repository: NotesRepository,
    val navigationManager: NavigationManager
) : NotesListPresenter {
    override fun loadNotes(): List<Note> {
        return repository.getNotes()
    }

    override fun navigateNoteEditor(noteId: Long?) {
        if (noteId == null) {
            navigationManager.showNoteEditor()
        } else {
            navigationManager.showNoteEditor(noteId)
        }
    }

    override fun deleteNote(note: Note) {
        return repository.deleteNote(note)
    }

}