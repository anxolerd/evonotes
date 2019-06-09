package io.github.anxolerd.evonotes.mvp.noteslist

import io.github.anxolerd.evonotes.dto.Note
import io.github.anxolerd.evonotes.mvp.BaseView

interface NotesListView : BaseView<NotesListView, NotesListPresenter> {
    fun showEmptyView()
    fun showLoading()
    fun showNotes(notes: List<Note>)
}