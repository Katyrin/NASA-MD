package com.katyrin.nasa_md.view.findsatellitephoto

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.jakewharton.rxbinding4.widget.textChanges
import com.katyrin.nasa_md.R
import com.katyrin.nasa_md.databinding.FragmentFindSatellitePhotoBinding
import com.katyrin.nasa_md.model.data.Coordinate
import com.katyrin.nasa_md.model.data.SatellitePhotoDTO
import com.katyrin.nasa_md.presenter.findsatellitephoto.FindSatellitePhotoPresenter
import com.katyrin.nasa_md.presenter.findsatellitephoto.FindSatellitePhotoView
import com.katyrin.nasa_md.utils.hideKeyboard
import com.katyrin.nasa_md.utils.toast
import com.katyrin.nasa_md.view.abs.AbsFragment
import com.katyrin.nasa_md.view.satellitephoto.SatellitePhotoScreen
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class FindSatellitePhotoFragment : AbsFragment(R.layout.fragment_find_satellite_photo),
    FindSatellitePhotoView {

    @Inject
    @InjectPresenter
    lateinit var presenter: FindSatellitePhotoPresenter

    @ProvidePresenter
    fun providePresenter(): FindSatellitePhotoPresenter = presenter

    private var binding: FragmentFindSatellitePhotoBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentFindSatellitePhotoBinding.inflate(inflater, container, false)
        .also { binding = it }.root

    override fun init() {
        subscribeEditTextViews()
        initViews()
    }

    override fun showErrorLat() {
        binding?.latitudeEditText?.error = getString(R.string.invalid_latitude)
        binding?.searchButton?.isEnabled = false
    }

    override fun showErrorLong() {
        binding?.longitudeEditText?.error = getString(R.string.invalid_longitude)
        binding?.searchButton?.isEnabled = false
    }

    override fun enabledButton() {
        binding?.searchButton?.isEnabled = true
    }

    override fun showLoading() {
        binding?.progressBar?.isVisible = true
        binding?.latitudeLayout?.isVisible = false
        binding?.longitudeLayout?.isVisible = false
        binding?.searchButton?.isVisible = false
    }

    override fun showNormalState() {
        binding?.progressBar?.isVisible = false
        binding?.latitudeLayout?.isVisible = true
        binding?.longitudeLayout?.isVisible = true
        binding?.searchButton?.isVisible = true
    }

    override fun showRequestError(message: String?) {
        toast(message)
    }

    override fun openNewFragment(satellitePhotoDTO: SatellitePhotoDTO) {
        presenter.navigateToScreen(SatellitePhotoScreen(satellitePhotoDTO))
    }

    private fun subscribeEditTextViews() {
        binding?.latitudeEditText
            ?.textChanges()
            ?.map { Coordinate.LAT to it.toString() }
            ?.let { presenter.subscribeLatLongChanged(it) }
        binding?.longitudeEditText
            ?.textChanges()
            ?.map { Coordinate.LONG to it.toString() }
            ?.let { presenter.subscribeLatLongChanged(it) }
    }

    private fun initViews() {
        binding?.longitudeEditText?.setOnEditorActionListener { _, _, _ ->
            requireActivity().hideKeyboard()
            requestSatellitePhoto()
            true
        }
        binding?.searchButton?.setOnClickListener {
            requestSatellitePhoto()
        }
    }

    private fun requestSatellitePhoto() {
        presenter.getSatellitePhoto(
            binding?.latitudeEditText?.text.toString().toFloat(),
            binding?.longitudeEditText?.text.toString().toFloat()
        )
    }

    override fun onDetach() {
        binding = null
        super.onDetach()
    }

    companion object {
        fun newInstance(): Fragment = FindSatellitePhotoFragment()
    }
}