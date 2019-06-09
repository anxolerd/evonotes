package io.github.anxolerd.evonotes.mvp.noteslist

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.github.anxolerd.evonotes.R
import io.github.anxolerd.evonotes.dto.Note


class NotesListFragment : androidx.fragment.app.Fragment(),
    NotesListView,
    NotesAdapter.OnItemClickListener,
    NotesAdapter.OnItemLongClickListener {

    // DI
    private var presenter: NotesListPresenter? = null

    // UI
    private var addNoteButton: FloatingActionButton? = null
    private var notesRecyclerView: RecyclerView? = null
    private var notesStatusText: TextView? = null

    // Data
    private var notes: MutableList<Note> = ArrayList()
    private val notesAdapter = NotesAdapter(notes)

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
        this.notesRecyclerView?.addItemDecoration(
            DividerItemDecoration(
                this.notesRecyclerView?.context,
                DividerItemDecoration.VERTICAL
            )
        )
        this.notesAdapter.addOnItemClickListener(this)
        this.notesAdapter.addOnItemLongClickListener(this)

        this.addNoteButton = view.findViewById(R.id.add_note_button)
        this.addNoteButton?.setOnClickListener {
            this.presenter!!.navigateNoteEditor()
        }

        this.notesStatusText = view.findViewById(R.id.notes_status_text)

        return view
    }

    override fun onItemClick(note: Note) {
        this.presenter!!.navigateNoteEditor(note.id)
    }

    override fun onItemLongClick(note: Note): Boolean {
        val dialogBuilder = AlertDialog.Builder(activity)
        dialogBuilder.setTitle(getString(io.github.anxolerd.evonotes.R.string.delete_dialog_title))
        dialogBuilder.setPositiveButton(
            getString(io.github.anxolerd.evonotes.R.string.delete)
        ) { dialog, _ ->
            this.presenter?.deleteNote(note)
            dialog.dismiss()
        }
        dialogBuilder.setNegativeButton(android.R.string.cancel) { dialog, _ ->
            dialog.dismiss()
        }
        dialogBuilder.create().show()
        return true
    }


    override fun onResume() {
        super.onResume()
        this.presenter?.loadNotes()
    }

    override fun showEmptyView() {
        this.notes.clear()
        this.notesAdapter.notifyDataSetChanged()
        this.notesRecyclerView?.visibility = View.GONE
        this.notesStatusText?.text = getString(R.string.empty_notes)
        this.notesStatusText?.visibility = View.VISIBLE
        this.addNoteButton?.isEnabled = true
    }

    override fun showLoading() {
        this.notesRecyclerView?.visibility = View.GONE
        this.notesStatusText?.text = getString(R.string.loading)
        this.notesStatusText?.visibility = View.VISIBLE
        this.addNoteButton?.isEnabled = false
    }

    override fun showNotes(notes: List<Note>) {
        this.notes.clear()
        this.notes.addAll(notes)
        this.notesAdapter.notifyDataSetChanged()
        this.notesStatusText?.visibility = View.GONE
        this.notesRecyclerView?.visibility = View.VISIBLE
        this.addNoteButton?.isEnabled = true
    }

    companion object {
        @JvmStatic
        fun newInstance() = NotesListFragment()
    }
}
