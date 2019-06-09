package io.github.anxolerd.evonotes.mvp.editnote

import io.github.anxolerd.evonotes.dto.Note
import io.github.anxolerd.evonotes.navigation.NavigationManager
import io.github.anxolerd.evonotes.repository.NotesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDateTime

class EditNotePresenterImpl(
    private val repository: NotesRepository,
    private val navigationManager: NavigationManager
) : EditNotePresenter {
    private var view: EditNoteView? = null

    // Threading
    val uiScope = CoroutineScope(Dispatchers.Main)
    val bgDispatcher = Dispatchers.IO


    override fun setView(view: EditNoteView) {
        this.view = view
    }

    override fun getNote(noteId: Long) = uiScope.launch {
        val note = withContext(bgDispatcher) {
            return@withContext checkNotNull(repository.getNote(noteId))
        }
        this@EditNotePresenterImpl.view?.showNote(note)
    }

    override fun saveNote(noteId: Long?, content: String) = uiScope.launch {
        if (noteId == null) {
            val newNote = Note(
                dateCreated = LocalDateTime.now(),
                dateModified = LocalDateTime.now(),
                content = content
            )
            withContext(bgDispatcher) {
                repository.saveNote(newNote)
            }
        } else {
            withContext(bgDispatcher) {
                val existingNote = checkNotNull(repository.getNote(noteId))
                existingNote.content = content
                existingNote.dateModified = LocalDateTime.now()
                repository.editNote(existingNote)
            }
        }
        this@EditNotePresenterImpl.navigateToNotesList()
    }

    override fun navigateToNotesList() {
        navigationManager.showNotesList()
    }
}