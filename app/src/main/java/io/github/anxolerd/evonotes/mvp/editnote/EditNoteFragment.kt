package io.github.anxolerd.evonotes.mvp.editnote

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.EditText
import androidx.appcompat.widget.ShareActionProvider
import androidx.core.view.MenuItemCompat
import io.github.anxolerd.evonotes.R
import io.github.anxolerd.evonotes.dto.Note


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
    private var shareActionProvider: ShareActionProvider? = null
    private var shareIntent: Intent? = null

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
        noteEditText?.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                setShareIntent(s.toString())
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
            }
        })

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_edit, menu)
        val shareItem = menu?.findItem(R.id.action_share)
        shareActionProvider = MenuItemCompat.getActionProvider(shareItem) as ShareActionProvider?
        shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent!!.type = "text/plain"
        setShareIntent(this.noteEditText?.text.toString())
    }

    private fun setShareIntent(text: String) {
        this@EditNoteFragment.shareIntent?.putExtra(Intent.EXTRA_TEXT, text)
        this@EditNoteFragment.shareActionProvider?.setShareIntent(shareIntent)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                this.presenter!!.navigateToNotesList()
                true
            }
            R.id.action_save -> {
                this.presenter?.saveNote(
                    this.noteId,
                    this.noteEditText?.text.toString()
                )
                true
            }
            else -> false
        }
    }

    override fun onResume() {
        super.onResume()
        this.noteId?.let {
            this.presenter?.getNote(it)
        }
    }

    override fun showNote(note: Note) {
        this.noteEditText?.setText(note.content)
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
