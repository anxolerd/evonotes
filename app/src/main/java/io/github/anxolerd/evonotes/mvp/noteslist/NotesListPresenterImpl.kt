package io.github.anxolerd.evonotes.mvp.noteslist

import io.github.anxolerd.evonotes.dto.Note
import io.github.anxolerd.evonotes.navigation.NavigationManager
import io.github.anxolerd.evonotes.repository.NotesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NotesListPresenterImpl(
    val repository: NotesRepository,
    val navigationManager: NavigationManager
) : NotesListPresenter {
    private var view: NotesListView? = null

    // Threading
    val uiScope = CoroutineScope(Dispatchers.Main)
    val bgDispatcher = Dispatchers.IO

    override fun setView(view: NotesListView) {
        this.view = view
    }

    override fun loadNotes() = uiScope.launch {
        view?.showLoading()
        val notes = withContext(bgDispatcher) {
            return@withContext repository.getNotes()
        }
        when(notes.size) {
            0 -> view?.showEmptyView()
            else -> view?.showNotes(notes)
        }
    }

    override fun navigateNoteEditor(noteId: Long?) {
        if (noteId == null) {
            navigationManager.showNoteEditor()
        } else {
            navigationManager.showNoteEditor(noteId)
        }
    }

    override fun deleteNote(note: Note) = uiScope.launch {
        view?.showLoading()
        val notes = withContext(bgDispatcher){
            repository.deleteNote(note)
            return@withContext repository.getNotes()
        }
        when(notes.size) {
            0 -> view?.showEmptyView()
            else -> view?.showNotes(notes)
        }
    }

}