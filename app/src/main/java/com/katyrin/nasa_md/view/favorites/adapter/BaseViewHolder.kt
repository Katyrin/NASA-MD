package com.katyrin.nasa_md.view.favorites.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.katyrin.nasa_md.model.data.FavoriteContentEntity

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    ItemTouchHelperViewHolder {
    abstract fun bind(dataItem: FavoriteContentEntity, delegate: FavoritesAdapter.Delegate?)
}