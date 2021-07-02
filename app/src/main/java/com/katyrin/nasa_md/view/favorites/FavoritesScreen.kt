package com.katyrin.nasa_md.view.favorites

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.androidx.FragmentScreen

object FavoritesScreen : FragmentScreen {
    override fun createFragment(factory: FragmentFactory): Fragment =
        FavoritesFragment.newInstance()
}