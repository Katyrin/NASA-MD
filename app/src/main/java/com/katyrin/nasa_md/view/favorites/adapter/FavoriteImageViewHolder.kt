package com.katyrin.nasa_md.view.favorites.adapter

import android.graphics.Color
import androidx.core.content.ContextCompat
import com.katyrin.nasa_md.R
import com.katyrin.nasa_md.databinding.ItemFavoriteImageBinding
import com.katyrin.nasa_md.model.data.FavoriteContentEntity
import com.katyrin.nasa_md.utils.click
import com.katyrin.nasa_md.utils.startDrag

class FavoriteImageViewHolder(
    private val viewBinding: ItemFavoriteImageBinding
) : BaseViewHolder(viewBinding.root) {

    override fun bind(dataItem: FavoriteContentEntity, delegate: FavoritesAdapter.Delegate?) =
        with(viewBinding) {
            headerTextView.text = dataItem.date
            messageTextView.text = dataItem.description
            dragHandleImageView.startDrag { delegate?.onStartDrag(this@FavoriteImageViewHolder) }
            root.click { delegate?.onFavoritePicked(dataItem) }
            itemView.setBackgroundColor(
                ContextCompat.getColor(
                    viewBinding.root.context,
                    R.color.background_card_view_color
                )
            )
        }

    override fun onItemSelected() {
        itemView.setBackgroundColor(Color.LTGRAY)
    }

    override fun onItemClear() {
        itemView.setBackgroundColor(
            ContextCompat.getColor(
                viewBinding.root.context,
                R.color.background_card_view_color
            )
        )
    }
}