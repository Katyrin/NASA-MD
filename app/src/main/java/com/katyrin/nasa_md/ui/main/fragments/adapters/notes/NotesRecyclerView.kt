package com.katyrin.nasa_md.ui.main.fragments.adapters.notes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.katyrin.nasa_md.databinding.ItemImportentNoteBinding
import com.katyrin.nasa_md.databinding.ItemNoteBinding
import com.katyrin.nasa_md.ui.main.model.data.Note

class NotesRecyclerView(private val data: List<Note>,
                        private val onClick: (String) -> Unit
):  RecyclerView.Adapter<BaseViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_NOTE -> NoteViewHolder(
                ItemNoteBinding.inflate(layoutInflater, parent, false)
            )
            TYPE_IMPORTANT -> ImportantNoteViewHolder(
                ItemImportentNoteBinding.inflate(layoutInflater, parent, false),
                onClick
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


    companion object {
        private const val TYPE_NOTE = 0
        private const val TYPE_IMPORTANT = 1
    }
}