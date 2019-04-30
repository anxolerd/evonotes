package io.github.anxolerd.evonotes.mvp.noteslist

import io.github.anxolerd.evonotes.dto.Note
import io.github.anxolerd.evonotes.mvp.BasePresenter

interface NotesListPresenter : BasePresenter {
    fun loadNotes(): List<Note>
    fun navigateNoteEditor(noteId: Long? = null)
}