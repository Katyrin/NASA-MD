package com.katyrin.nasa_md.view.favorites.adapter

import android.graphics.Color
import androidx.core.content.ContextCompat
import com.katyrin.nasa_md.R
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
        itemView.setBackgroundColor(
            ContextCompat.getColor(
                viewBinding.root.context,
                R.color.notes_background
            )
        )
    }
}