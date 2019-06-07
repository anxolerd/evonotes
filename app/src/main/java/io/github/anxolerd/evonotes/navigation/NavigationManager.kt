package io.github.anxolerd.evonotes.navigation

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import io.github.anxolerd.evonotes.EvoNotesApp

interface NavigationManager {
    fun init(app: EvoNotesApp, fragmentManager: FragmentManager, activity: AppCompatActivity)

    fun showNotesList()
    fun showNoteEditor()
    fun showNoteEditor(noteId: Long)
}