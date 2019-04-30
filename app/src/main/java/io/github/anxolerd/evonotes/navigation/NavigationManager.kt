package io.github.anxolerd.evonotes.navigation

import androidx.fragment.app.FragmentManager
import io.github.anxolerd.evonotes.EvoNotesApp

interface NavigationManager {
    fun init(app: EvoNotesApp, fragmentManager: FragmentManager)

    fun showNotesList()
    fun showNoteEditor()
    fun showNoteEditor(noteId: Long)
}