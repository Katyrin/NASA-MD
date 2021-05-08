package com.katyrin.nasa_md.ui.main.fragments.adapters.notes

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import com.katyrin.nasa_md.databinding.ItemImportentNoteBinding
import com.katyrin.nasa_md.databinding.ItemNoteBinding
import com.katyrin.nasa_md.ui.main.model.data.Note

class NoteViewHolder(private val itemBinding: ItemNoteBinding,
                     private val onStartDrag: (RecyclerView.ViewHolder) -> Unit)
    : BaseViewHolder(itemBinding.root) {

    @SuppressLint("ClickableViewAccessibility")
    override fun bind(dataItem: Note) {
        itemBinding.apply {
            headerTextView.text = dataItem.header
            messageTextView.text = dataItem.description

            dragHandleImageView.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                    onStartDrag(this@NoteViewHolder)
                }
                false
            }
        }
    }

    override fun onItemSelected() {
        itemView.setBackgroundColor(Color.LTGRAY)

    }

    override fun onItemClear() {
        itemView.setBackgroundColor(0)

    }
}

class ImportantNoteViewHolder(private val itemBinding: ItemImportentNoteBinding,
                              private val onClick: (String) -> Unit,
                              private val onStartDrag: (RecyclerView.ViewHolder) -> Unit
) : BaseViewHolder(itemBinding.root) {

    @SuppressLint("ClickableViewAccessibility")
    override fun bind(dataItem: Note) {
        itemBinding.apply {
            headerTextView.text = dataItem.header
            messageTextView.text = dataItem.description
            positiveButton.setOnClickListener {
                onClick(positiveButton.text.toString())
            }
            negativeButton.setOnClickListener {
                onClick(negativeButton.text.toString())
            }

            dragHandleImageView.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                    onStartDrag(this@ImportantNoteViewHolder)
                }
                false
            }
        }
    }

    override fun onItemSelected() {
        itemView.setBackgroundColor(Color.LTGRAY)

    }

    override fun onItemClear() {
        itemView.setBackgroundColor(0)

    }
}