package com.katyrin.nasa_md.view.favorites.adapter

import android.graphics.Color
import com.katyrin.nasa_md.databinding.ItemFavoriteBinding
import com.katyrin.nasa_md.model.data.FavoriteContentEntity
import com.katyrin.nasa_md.utils.click
import com.katyrin.nasa_md.utils.startDrag
import com.katyrin.nasa_md.view.favorites.adapter.FavoritesAdapter.Delegate

class FavoriteViewHolder(
    private val viewBinding: ItemFavoriteBinding
) : BaseViewHolder(viewBinding.root) {

    override fun bind(dataItem: FavoriteContentEntity, delegate: Delegate?) =
        with(viewBinding) {
            headerTextView.text = dataItem.date
            messageTextView.text = dataItem.description
            dragHandleImageView.startDrag { delegate?.onStartDrag(this@FavoriteViewHolder) }
            root.click { delegate?.onFavoritePicked(dataItem) }
        }

    override fun onItemSelected() {
        itemView.setBackgroundColor(Color.LTGRAY)
    }

    override fun onItemClear() {
        itemView.setBackgroundColor(0)
    }
}