package com.katyrin.nasa_md.view.findsatellitephoto

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.androidx.FragmentScreen

object FindSatellitePhotoScreen : FragmentScreen {
    override fun createFragment(factory: FragmentFactory): Fragment =
        FindSatellitePhotoFragment.newInstance()
}