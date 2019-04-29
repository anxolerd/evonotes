package io.github.anxolerd.evonotes.mvp.editnote

import android.os.Bundle
import android.text.Editable
import android.view.*
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

import io.github.anxolerd.evonotes.R

private const val NOTE_ID = "note_id"
private const val NEW_NOTE_ID = -1L

class EditNoteFragment : androidx.fragment.app.Fragment(), EditNoteView {
    // DI
    private var presenter: EditNotePresenter? = null

    // UI
    private var noteEditText: EditText? = null

    override fun setPresenter(presenter: EditNotePresenter) {
        this.presenter = checkNotNull(presenter)
    }

    private var noteId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            noteId = when (it.getLong(NOTE_ID)) {
                NEW_NOTE_ID -> null
                else -> it.getLong(NOTE_ID)
            }
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_edit_note, container, false)
        noteEditText = view.findViewById(R.id.note_edit_text)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_edit, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_save -> {
                this.presenter!!.saveNote(this.noteId, this.noteEditText?.text.toString())
                this.presenter!!.navigateToNotesList()
                true
            }
            else -> false
        }
    }

    override fun onResume() {
        super.onResume()
        this.noteId?.let {
            val noteText = this.presenter!!.getNote(this.noteId!!).content
            this.noteEditText?.setText(noteText)
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = EditNoteFragment().apply {
            arguments = Bundle().apply {
                putLong(
                    NOTE_ID,
                    NEW_NOTE_ID
                )
            }
        }

        @JvmStatic
        fun newInstance(noteId: Long) =
            EditNoteFragment().apply {
                arguments = Bundle().apply {
                    putLong(NOTE_ID, noteId)
                }
            }
    }
}
