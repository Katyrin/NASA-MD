package com.katyrin.nasa_md.view.favorites

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.katyrin.nasa_md.model.data.FavoriteContentEntity

class FavoriteContentScreen(
    private val favoriteContentEntity: FavoriteContentEntity
) : FragmentScreen {
    override fun createFragment(factory: FragmentFactory): Fragment =
        FavoriteContentFragment.newInstance(favoriteContentEntity)
}