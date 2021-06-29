package com.katyrin.nasa_md.view.favorites.adapter

import android.view.LayoutInflater.from
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.katyrin.nasa_md.databinding.ItemFavoriteBinding
import com.katyrin.nasa_md.databinding.ItemFavoriteImageBinding
import com.katyrin.nasa_md.model.data.FavoriteContentEntity

class FavoritesAdapter(
    private val delegate: Delegate?
) : ListAdapter<FavoriteContentEntity, BaseViewHolder>(FavoriteDiffing), ItemTouchHelperAdapter {

    interface Delegate {
        fun onFavoritePicked(favoriteContentEntity: FavoriteContentEntity)
        fun onStartDrag(viewHolder: BaseViewHolder)
        fun onDeleteFavorite(favoriteContentEntity: FavoriteContentEntity)
        fun onSaveNewList(newList: List<FavoriteContentEntity>)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
        when (viewType) {
            IMAGE -> FavoriteImageViewHolder(
                ItemFavoriteImageBinding.inflate(from(parent.context), parent, false)
            )
            NO_IMAGE -> FavoriteViewHolder(
                ItemFavoriteBinding.inflate(from(parent.context), parent, false)
            )
            else -> throw IllegalArgumentException(UNKNOWN_ITEM_TYPE)
        }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) =
        holder.bind(getItem(position), delegate)

    override fun getItemViewType(position: Int): Int =
        if (getItem(position).url.split(CHAR_DOT)[1] == NASA) IMAGE else NO_IMAGE

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        val newList = currentList.toMutableList()
        newList.removeAt(fromPosition)
            .apply { newList.add(toPosition, this) }
        submitList(newList)
        delegate?.onSaveNewList(newList)
    }

    override fun onItemDismiss(position: Int) {
        val newList = currentList.toMutableList()
        delegate?.onDeleteFavorite(currentList[position])
        newList.removeAt(position)
        submitList(newList)
    }

    private companion object {
        const val IMAGE = 1
        const val NO_IMAGE = 2
        const val NASA = "nasa"
        const val CHAR_DOT = '.'
        const val UNKNOWN_ITEM_TYPE = "Unknown item type"
    }
}