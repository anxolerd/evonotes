package io.github.anxolerd.evonotes.navigation

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import io.github.anxolerd.evonotes.EvoNotesApp
import io.github.anxolerd.evonotes.mvp.editnote.EditNoteFragment
import io.github.anxolerd.evonotes.mvp.noteslist.NotesListFragment


class NavigationManagerImpl : NavigationManager {

    private var fm: FragmentManager? = null
    private var app: EvoNotesApp? = null
    private var activity: AppCompatActivity? = null

    private fun open(fragment: Fragment) {
        if (fm == null) return
        fm!!.beginTransaction()
            .replace(io.github.anxolerd.evonotes.R.id.fragment_container, fragment)
            .addToBackStack(fragment.toString())
            .commit()
    }

    private fun openAsRoot(fragment: Fragment) {
        popEveryFragment()
        open(fragment)
    }

    private fun popEveryFragment() {
        if (fm == null) return
        val backStackCount = fm!!.backStackEntryCount
        for (index in 0 until backStackCount) {

            val backStackId = fm!!.getBackStackEntryAt(index).id
            fm?.popBackStack(backStackId, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }

    override fun init(app: EvoNotesApp, fragmentManager: FragmentManager, activity: AppCompatActivity) {
        this.app = app
        this.fm = fragmentManager
        this.activity = activity
        fragmentManager.addOnBackStackChangedListener {
            if (fragmentManager.backStackEntryCount == 0) {
                System.exit(0)  // TODO: find a better way to exit app
            }
        }
    }

    override fun showNoteEditor() {
        val fragment = EditNoteFragment.newInstance()
        val presenter = app!!.di.getEditNotePresenter()
        presenter.setView(fragment)
        fragment.setPresenter(presenter)

        activity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        open(fragment)
    }

    override fun showNoteEditor(noteId: Long) {
        val fragment = EditNoteFragment.newInstance(noteId)
        val presenter = app!!.di.getEditNotePresenter()
        presenter.setView(fragment)
        fragment.setPresenter(presenter)

        activity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        open(fragment)
    }

    override fun showNotesList() {
        val fragment = NotesListFragment.newInstance()
        val presenter = app!!.di.getNotesListPresenter()
        presenter.setView(fragment)
        fragment.setPresenter(presenter)

        activity?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        openAsRoot(fragment)
    }
}