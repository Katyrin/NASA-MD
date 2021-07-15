package com.katyrin.nasa_md.view.satellitephoto

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.katyrin.nasa_md.R
import com.katyrin.nasa_md.databinding.FragmentContentInfoBinding
import com.katyrin.nasa_md.model.data.SatellitePhotoDTO
import com.katyrin.nasa_md.presenter.satellitephoto.SatellitePhotoPresenter
import com.katyrin.nasa_md.presenter.satellitephoto.SatellitePhotoView
import com.katyrin.nasa_md.utils.toast
import com.katyrin.nasa_md.view.abs.AbsFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class SatellitePhotoFragment : AbsFragment(R.layout.fragment_content_info), SatellitePhotoView {

    @Inject
    @InjectPresenter
    lateinit var presenter: SatellitePhotoPresenter

    @ProvidePresenter
    fun providePresenter(): SatellitePhotoPresenter = presenter

    private var satellitePhotoDTO: SatellitePhotoDTO? = null
    private var binding: FragmentContentInfoBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentContentInfoBinding.inflate(inflater, container, false)
        .also { binding = it }.root

    override fun init() {
        arguments?.let { satellitePhotoDTO = it.getParcelable(SATELLITE_PHOTO_DTO) }
        initViews()
    }

    override fun successSaveState() {
        binding?.favoriteButton?.isVisible = false
        binding?.unFavoriteButton?.isVisible = true
    }

    override fun successDeleteState() {
        binding?.favoriteButton?.isVisible = true
        binding?.unFavoriteButton?.isVisible = false
    }

    override fun showError(message: String?) {
        toast(message)
    }

    private fun initViews() {
        binding?.dateTextView?.text = satellitePhotoDTO?.date
        satellitePhotoDTO?.url?.let { showSatellitePhoto(it) }
        binding?.favoriteButton?.setOnClickListener {
            satellitePhotoDTO?.let { data -> presenter.saveSatellitePhoto(data) }
        }
        binding?.unFavoriteButton?.setOnClickListener {
            satellitePhotoDTO?.let { data -> presenter.deleteSatellitePhoto(data) }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun showSatellitePhoto(url: String) {
        binding?.apply {
            imageView.isVisible = false
            (webView as WebView).apply {
                isVisible = true
                settings.javaScriptEnabled = true

                settings.builtInZoomControls = true
                settings.useWideViewPort = true
                settings.loadWithOverviewMode = true

                loadUrl(url)
            }
        }
    }

    override fun onDetach() {
        satellitePhotoDTO = null
        binding = null
        super.onDetach()
    }

    companion object {
        private const val SATELLITE_PHOTO_DTO = "SATELLITE_PHOTO_DTO"

        fun newInstance(satellitePhotoDTO: SatellitePhotoDTO): Fragment =
            SatellitePhotoFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(SATELLITE_PHOTO_DTO, satellitePhotoDTO)
                }
            }
    }
}