package com.katyrin.nasa_md.ui.main.fragments.adapters.notes

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.katyrin.nasa_md.ui.main.data.ListItem

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(dataItem: ListItem)
}