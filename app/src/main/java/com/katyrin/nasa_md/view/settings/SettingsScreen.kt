package com.katyrin.nasa_md.view.settings

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.androidx.FragmentScreen

object SettingsScreen : FragmentScreen {
    override fun createFragment(factory: FragmentFactory): Fragment = SettingsFragment.newInstance()
}