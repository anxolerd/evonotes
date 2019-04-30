package io.github.anxolerd.evonotes.di

import android.content.Context
import io.github.anxolerd.evonotes.mvp.editnote.EditNotePresenter
import io.github.anxolerd.evonotes.mvp.editnote.EditNotePresenterImpl
import io.github.anxolerd.evonotes.mvp.noteslist.NotesListPresenter
import io.github.anxolerd.evonotes.mvp.noteslist.NotesListPresenterImpl
import io.github.anxolerd.evonotes.navigation.NavigationManager
import io.github.anxolerd.evonotes.navigation.NavigationManagerImpl
import io.github.anxolerd.evonotes.repository.NotesRepository
import io.github.anxolerd.evonotes.repository.RoomNotesRepositoryImpl


class InjectorImpl(private val appContext: Context) : Injector {
    override fun getNotesRepository(): NotesRepository {
        if (notesRepository == null) {
            notesRepository = RoomNotesRepositoryImpl(appContext)
        }
        return notesRepository!!
    }

    override fun getNavigationManager(): NavigationManager {
        return navigationManager
    }

    override fun getNotesListPresenter(): NotesListPresenter {
        if (notesListPresenter == null) {
            notesListPresenter = NotesListPresenterImpl(
                navigationManager = this.getNavigationManager(),
                repository = this.getNotesRepository()
            )
        }
        return notesListPresenter!!
    }

    override fun getEditNotePresenter(): EditNotePresenter {
        if (editNotePresenter == null) {
            editNotePresenter = EditNotePresenterImpl(
                navigationManager = this.getNavigationManager(),
                repository = this.getNotesRepository()
            )
        }
        return editNotePresenter!!
    }

    companion object {
        private var notesRepository: NotesRepository? = null
        private val navigationManager: NavigationManager by lazy { NavigationManagerImpl() }

        // presenters
        private var notesListPresenter: NotesListPresenter? = null
        private var editNotePresenter: EditNotePresenter? = null
    }
}