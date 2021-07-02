package com.katyrin.nasa_md.view.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.androidx.FragmentScreen

object HomeScreen : FragmentScreen {
    override fun createFragment(factory: FragmentFactory): Fragment = HomeFragment.newInstance()
}