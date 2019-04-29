package io.github.anxolerd.evonotes.mvp.noteslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.github.anxolerd.evonotes.R
import io.github.anxolerd.evonotes.dto.Note


class NotesListFragment : androidx.fragment.app.Fragment(), NotesListView, NotesAdapter.OnItemClickListener {
    // DI
    private var presenter: NotesListPresenter? = null

    // UI
    private var addNoteButton: FloatingActionButton? = null
    private var notesRecyclerView: RecyclerView? = null

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
        this.notesAdapter.addOnItemClickListener(this)

        this.addNoteButton = view.findViewById(R.id.add_note_button)
        this.addNoteButton?.setOnClickListener {
            this.presenter!!.navigateNoteEditor()
        }

        return view
    }

    override fun onItemClick(note: Note) {
        this.presenter!!.navigateNoteEditor(note.id)
    }


    override fun onResume() {
        super.onResume()
        val notes = this.presenter!!.loadNotes()
        this.notes.clear()
        this.notes.addAll(notes)
        this.notesAdapter.notifyDataSetChanged()
    }


    companion object {
        @JvmStatic
        fun newInstance() = NotesListFragment()
    }
}
