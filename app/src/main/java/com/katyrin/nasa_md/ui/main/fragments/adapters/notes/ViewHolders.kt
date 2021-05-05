package com.katyrin.nasa_md.ui.main.fragments.adapters.notes

import android.view.View
import com.katyrin.nasa_md.databinding.ItemImportentNoteBinding
import com.katyrin.nasa_md.databinding.ItemNoteBinding
import com.katyrin.nasa_md.ui.main.data.ListItem

class NoteViewHolder(private val itemBinding: ItemNoteBinding)
    : BaseViewHolder(itemBinding.root) {
    override fun bind(dataItem: ListItem) {
        dataItem as ListItem.NoteItem

        itemBinding.apply {
            if (dataItem.hasPortfolio) {
                guidLine.setGuidelineEnd(40)
                hasInfoIcon.visibility = View.VISIBLE
            } else {
                guidLine.setGuidelineEnd(40)
                hasInfoIcon.visibility = View.GONE
            }
            headerTextView.text = dataItem.header
            messageTextView.text = dataItem.description
        }
    }
}

class ImportantNoteViewHolder(private val itemBinding: ItemImportentNoteBinding,
                       private val onClick: (String) -> Unit
) : BaseViewHolder(itemBinding.root) {
    override fun bind(dataItem: ListItem) {
        dataItem as ListItem.ImportantItem
        itemBinding.apply {
            headerTextView.text = dataItem.header
            messageTextView.text = dataItem.description
            positiveButton.setOnClickListener {
                onClick(positiveButton.text.toString())
            }
            negativeButton.setOnClickListener {
                onClick(negativeButton.text.toString())
            }
        }
    }
}