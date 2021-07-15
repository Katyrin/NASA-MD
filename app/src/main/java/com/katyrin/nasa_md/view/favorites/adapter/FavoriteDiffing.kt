package com.katyrin.nasa_md.view.favorites.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.katyrin.nasa_md.model.data.FavoriteContentEntity

object FavoriteDiffing : DiffUtil.ItemCallback<FavoriteContentEntity>() {

    private val payload = Any()

    override fun areItemsTheSame(
        oldItem: FavoriteContentEntity,
        newItem: FavoriteContentEntity
    ): Boolean = oldItem.url == newItem.url

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(
        oldItem: FavoriteContentEntity,
        newItem: FavoriteContentEntity
    ): Boolean = oldItem == newItem

    override fun getChangePayload(oldItem: FavoriteContentEntity, newItem: FavoriteContentEntity) =
        payload

}

