package com.katyrin.nasa_md.view.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.androidx.FragmentScreen

object ViewPagerScreen : FragmentScreen {
    override fun createFragment(factory: FragmentFactory): Fragment =
        ViewPagerFragment.newInstance()
}