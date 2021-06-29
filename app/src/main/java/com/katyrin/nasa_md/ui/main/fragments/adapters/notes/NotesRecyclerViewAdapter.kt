package com.katyrin.nasa_md.ui.main.fragments.adapters.notes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.katyrin.nasa_md.databinding.ItemFavoriteBinding
import com.katyrin.nasa_md.databinding.ItemFavoriteImageBinding
import com.katyrin.nasa_md.ui.main.model.data.Note
import com.katyrin.nasa_md.view.favorites.adapter.ItemTouchHelperAdapter

class NotesRecyclerViewAdapter(private val onClick: (String) -> Unit,
                               private val onDeleteNote: (Note) -> Unit,
                               private val onStartDrag: (BaseViewHolder) -> Unit
):  RecyclerView.Adapter<BaseViewHolder>(), ItemTouchHelperAdapter {

    private var data: MutableList<Note> = mutableListOf()

    fun setData(newData: MutableList<Note>) {
        data = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_NOTE -> NoteViewHolder(
                ItemFavoriteBinding.inflate(layoutInflater, parent, false),
                onStartDrag
            )
            TYPE_IMPORTANT -> ImportantNoteViewHolder(
                ItemFavoriteImageBinding.inflate(layoutInflater, parent, false),
                onClick,
                onStartDrag
            )
            else -> throw IllegalArgumentException("Unknown item type")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    override fun getItemViewType(position: Int): Int {
        return when {
            !data[position].isImportant -> TYPE_NOTE
            data[position].isImportant -> TYPE_IMPORTANT
            else -> throw IllegalArgumentException("Unknown item type")
        }
    }

    fun appendItem(note: Note) {
        data.add(note)
        notifyItemInserted(itemCount - 1)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        data.removeAt(fromPosition).apply {
            data.add(if (toPosition > fromPosition) toPosition else toPosition, this)
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        onDeleteNote(data[position])
        data.removeAt(position)
        notifyItemRemoved(position)
    }

    companion object {
        private const val TYPE_NOTE = 0
        private const val TYPE_IMPORTANT = 1
    }
}