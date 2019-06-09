package io.github.anxolerd.evonotes.mvp.noteslist

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.github.anxolerd.evonotes.R
import io.github.anxolerd.evonotes.dto.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class NotesListFragment : androidx.fragment.app.Fragment(),
    NotesListView,
    NotesAdapter.OnItemClickListener,
    NotesAdapter.OnItemLongClickListener {

    // DI
    private var presenter: NotesListPresenter? = null

    // UI
    private var addNoteButton: FloatingActionButton? = null
    private var notesRecyclerView: RecyclerView? = null

    // Data
    private var notes: MutableList<Note> = ArrayList()
    private val notesAdapter = NotesAdapter(notes)

    // Threading
    val uiScope = CoroutineScope(Dispatchers.Main)
    val bgDispatcher = Dispatchers.IO

    override fun setPresenter(presenter: NotesListPresenter) {
        this.presenter = checkNotNull(presenter)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_notes_list, container, false)

        this.notesRecyclerView = view.findViewById(R.id.notes_recycler_view)
        this.notesRecyclerView?.layoutManager = LinearLayoutManager(inflater.context)
        this.notesRecyclerView?.adapter = notesAdapter
        this.notesAdapter.addOnItemClickListener(this)
        this.notesAdapter.addOnItemLongClickListener(this)

        this.addNoteButton = view.findViewById(R.id.add_note_button)
        this.addNoteButton?.setOnClickListener {
            this.presenter!!.navigateNoteEditor()
        }

        return view
    }

    override fun onItemClick(note: Note) {
        this.presenter!!.navigateNoteEditor(note.id)
    }

    override fun onItemLongClick(note: Note): Boolean {
        val dialogBuilder = AlertDialog.Builder(activity)
        dialogBuilder.setTitle(getString(io.github.anxolerd.evonotes.R.string.delete_dialog_title))
        dialogBuilder.setPositiveButton(
            getString(io.github.anxolerd.evonotes.R.string.delete),
            { dialog, _ ->
                this.deleteNote(note)
                this.loadData()
                dialog.dismiss()
            }
        )
        dialogBuilder.setNegativeButton(android.R.string.cancel, { dialog, _ ->
            dialog.dismiss()
        })
        dialogBuilder.create().show()
        return true
    }


    override fun onResume() {
        super.onResume()
        this.loadData()
    }

    fun deleteNote(note: Note) = uiScope.launch {
        withContext(bgDispatcher) {
            return@withContext this@NotesListFragment.presenter!!.deleteNote(note)
        }
    }

    // TODO: move to presenter?
    fun loadData() = uiScope.launch {
        val notes = withContext(bgDispatcher) {
            return@withContext this@NotesListFragment.presenter!!.loadNotes()
        }
        this@NotesListFragment.notes.clear()
        this@NotesListFragment.notes.addAll(notes)
        this@NotesListFragment.notesAdapter.notifyDataSetChanged()
    }

    companion object {
        @JvmStatic
        fun newInstance() = NotesListFragment()
    }
}
