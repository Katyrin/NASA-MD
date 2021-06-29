package com.katyrin.nasa_md.ui.main.fragments.adapters.notes

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.MotionEvent
import androidx.core.content.ContextCompat
import com.katyrin.nasa_md.R
import com.katyrin.nasa_md.databinding.ItemFavoriteBinding
import com.katyrin.nasa_md.databinding.ItemFavoriteImageBinding
import com.katyrin.nasa_md.ui.main.model.data.Note

class NoteViewHolder(private val itemBinding: ItemFavoriteBinding,
                     private val onStartDrag: (BaseViewHolder) -> Unit
) : BaseViewHolder(itemBinding.root) {

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

class ImportantNoteViewHolder(private val itemBinding: ItemFavoriteImageBinding,
                              private val onClick: (String) -> Unit,
                              private val onStartDrag: (BaseViewHolder) -> Unit
) : BaseViewHolder(itemBinding.root) {

    private val context = itemBinding.root.context

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
        itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.background_card_view_color))
    }

    override fun onItemSelected() {
        itemView.setBackgroundColor(Color.LTGRAY)
    }

    override fun onItemClear() {
        itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.background_card_view_color))
    }
}