package io.github.anxolerd.evonotes.mvp.editnote

import io.github.anxolerd.evonotes.dto.Note
import io.github.anxolerd.evonotes.mvp.BaseView

interface EditNoteView : BaseView<EditNoteView, EditNotePresenter> {
    fun showNote(note: Note)
}