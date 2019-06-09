package io.github.anxolerd.evonotes.mvp.noteslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.github.anxolerd.evonotes.R
import io.github.anxolerd.evonotes.dto.Note
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter


class NotesAdapter(private var notes: List<Note>) : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {
    var onItemClickListener: OnItemClickListener? = null
    var onItemLongClickListener: OnItemLongClickListener? = null

    fun addOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

    fun addOnItemLongClickListener(listener: OnItemLongClickListener) {
        this.onItemLongClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_list_item, parent, false)

        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note: Note = this.notes.get(position)
        holder.bindNote(note)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val contentView: TextView = itemView.findViewById(R.id.content_view)
        val timestampView: TextView = itemView.findViewById(R.id.datetime_view)
        var note: Note? = null

        init {
            itemView.setOnClickListener {
                if (this.note != null) {
                    this@NotesAdapter.onItemClickListener?.onItemClick(note!!)
                }
            }

            itemView.setOnLongClickListener {
                return@setOnLongClickListener if (this.note != null && this@NotesAdapter.onItemLongClickListener != null) {
                    this@NotesAdapter.onItemLongClickListener?.onItemLongClick(note!!)!!
                } else false
            }

        }

        fun bindNote(note: Note) {
            this.note = note

            this.contentView.text = note.content

            val timestampText = if (note.dateModified.isBefore(LocalDateTime.now().minusDays(1))) {
                DateTimeFormatter.ofPattern("MM/dd").format(note.dateModified)
            } else {
                DateTimeFormatter.ofPattern("HH:mm").format(note.dateModified)
            }
            this.timestampView.text = timestampText
        }

    }

    interface OnItemClickListener {
        fun onItemClick(note: Note)
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(note: Note): Boolean
    }
}