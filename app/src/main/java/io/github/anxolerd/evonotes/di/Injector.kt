package io.github.anxolerd.evonotes.di

import io.github.anxolerd.evonotes.mvp.editnote.EditNotePresenter
import io.github.anxolerd.evonotes.mvp.noteslist.NotesListPresenter
import io.github.anxolerd.evonotes.navigation.NavigationManager
import io.github.anxolerd.evonotes.repository.NotesRepository

interface Injector {
    fun getNavigationManager(): NavigationManager
    fun getNotesRepository(): NotesRepository

    fun getNotesListPresenter(): NotesListPresenter
    fun getEditNotePresenter(): EditNotePresenter
}