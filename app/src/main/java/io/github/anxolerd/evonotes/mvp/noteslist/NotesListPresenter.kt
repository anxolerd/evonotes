package io.github.anxolerd.evonotes.mvp.noteslist

import io.github.anxolerd.evonotes.dto.Note
import io.github.anxolerd.evonotes.mvp.BasePresenter
import kotlinx.coroutines.Job

interface NotesListPresenter : BasePresenter<NotesListPresenter, NotesListView> {
    fun loadNotes(): Job
    fun navigateNoteEditor(noteId: Long? = null)
    fun deleteNote(note: Note): Job
}