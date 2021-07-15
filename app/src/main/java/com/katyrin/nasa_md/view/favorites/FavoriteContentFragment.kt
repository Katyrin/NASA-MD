package com.katyrin.nasa_md.view.favorites

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
import com.katyrin.nasa_md.model.data.FavoriteContentEntity
import com.katyrin.nasa_md.presenter.favoritecontent.FavoriteContentPresenter
import com.katyrin.nasa_md.presenter.favoritecontent.FavoriteContentView
import com.katyrin.nasa_md.utils.setImageFromUri
import com.katyrin.nasa_md.utils.toast
import com.katyrin.nasa_md.view.abs.AbsFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class FavoriteContentFragment : AbsFragment(R.layout.fragment_content_info), FavoriteContentView {

    @Inject
    @InjectPresenter
    lateinit var presenter: FavoriteContentPresenter

    @ProvidePresenter
    fun providePresenter(): FavoriteContentPresenter = presenter

    private var binding: FragmentContentInfoBinding? = null
    private var favoriteContentEntity: FavoriteContentEntity? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentContentInfoBinding.inflate(inflater, container, false)
        .also { binding = it }.root

    override fun init() {
        arguments?.let { favoriteContentEntity = it.getParcelable(FAVORITE_CONTENT) }
        favoriteContentEntity?.url?.let { presenter.checkFavoriteContent(it) }
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

    override fun showImage(url: String) {
        binding?.webView?.isVisible = false
        binding?.imageView?.isVisible = true
        binding?.imageView?.setImageFromUri(url)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun showWebView(url: String) {
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

    override fun showError(message: String?) {
        toast(message)
    }

    private fun initViews() {
        binding?.favoriteButton?.isVisible = false
        binding?.unFavoriteButton?.isVisible = true
        binding?.dateTextView?.text = favoriteContentEntity?.date
        binding?.favoriteButton?.setOnClickListener {
            favoriteContentEntity?.let { data -> presenter.saveFavoriteContent(data) }
        }
        binding?.unFavoriteButton?.setOnClickListener {
            favoriteContentEntity?.let { data -> presenter.deleteFavoriteContent(data) }
        }
    }

    override fun onDetach() {
        binding = null
        favoriteContentEntity = null
        super.onDetach()
    }

    companion object {
        private const val FAVORITE_CONTENT = "FAVORITE_CONTENT"

        fun newInstance(favoriteContentEntity: FavoriteContentEntity): Fragment =
            FavoriteContentFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(FAVORITE_CONTENT, favoriteContentEntity)
                }
            }
    }
}