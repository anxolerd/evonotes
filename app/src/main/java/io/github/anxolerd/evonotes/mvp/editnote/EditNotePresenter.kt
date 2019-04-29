package io.github.anxolerd.evonotes.mvp.editnote

import io.github.anxolerd.evonotes.dto.Note
import io.github.anxolerd.evonotes.mvp.BasePresenter

public interface EditNotePresenter: BasePresenter {
    fun getNote(noteId: Long): Note

    fun saveNote(noteId: Long?, content: String): Note

    fun navigateToNotesList()
}