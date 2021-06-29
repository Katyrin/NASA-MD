package com.katyrin.nasa_md.ui.main.fragments.adapters.notes

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.katyrin.nasa_md.ui.main.model.data.Note
import com.katyrin.nasa_md.view.favorites.adapter.ItemTouchHelperViewHolder

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    ItemTouchHelperViewHolder {
    abstract fun bind(dataItem: Note)
}