package com.katyrin.nasa_md.view.satellitephoto

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.katyrin.nasa_md.model.data.SatellitePhotoDTO

class SatellitePhotoScreen(private val satellitePhotoDTO: SatellitePhotoDTO) : FragmentScreen {
    override fun createFragment(factory: FragmentFactory): Fragment =
        SatellitePhotoFragment.newInstance(satellitePhotoDTO)
}