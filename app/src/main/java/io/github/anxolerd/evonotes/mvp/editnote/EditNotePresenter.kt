package io.github.anxolerd.evonotes.mvp.editnote

import io.github.anxolerd.evonotes.mvp.BasePresenter
import kotlinx.coroutines.Job

interface EditNotePresenter : BasePresenter<EditNotePresenter, EditNoteView> {
    fun getNote(noteId: Long): Job
    fun saveNote(noteId: Long?, content: String): Job
    fun navigateToNotesList()
}