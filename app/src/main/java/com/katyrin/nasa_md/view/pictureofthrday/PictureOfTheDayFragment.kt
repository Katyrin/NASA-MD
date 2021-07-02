package com.katyrin.nasa_md.view.pictureofthrday

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.katyrin.nasa_md.R
import com.katyrin.nasa_md.databinding.FragmentContentInfoBinding
import com.katyrin.nasa_md.presenter.pictureoftheday.PictureOfTheDayPresenter
import com.katyrin.nasa_md.presenter.pictureoftheday.PictureOfTheDayView
import com.katyrin.nasa_md.utils.beginDelayedTransition
import com.katyrin.nasa_md.utils.setImageFromUri
import com.katyrin.nasa_md.utils.toast
import com.katyrin.nasa_md.view.abs.AbsFragment
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.subjects.BehaviorSubject
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class PictureOfTheDayFragment : AbsFragment(R.layout.fragment_content_info),
    PictureOfTheDayView {

    @Inject
    @InjectPresenter
    lateinit var presenter: PictureOfTheDayPresenter

    @ProvidePresenter
    fun providePresenter(): PictureOfTheDayPresenter = presenter

    private var binding: FragmentContentInfoBinding? = null
    private val _imageClick = BehaviorSubject.create<Boolean>()
    private val imageClick = _imageClick.toFlowable(BackpressureStrategy.LATEST)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentContentInfoBinding.inflate(inflater, container, false)
        .also { binding = it }.root

    override fun init() {
        arguments?.let {
            binding?.dateTextView?.text = it.getString(REQUIRE_DATE)
            presenter.getData(it.getString(REQUIRE_DATE))
        }

        presenter.subscribeDoubleImageClick(imageClick)
        binding?.imageView?.setOnClickListener {
            _imageClick.onNext(true)
        }
        initButtons()
    }

    private fun initButtons() {
        binding?.favoriteButton?.setOnClickListener {
            presenter.saveSatellitePhoto()
        }
        binding?.unFavoriteButton?.setOnClickListener {
            presenter.deleteSatellitePhoto()
        }
    }

    override fun successSaveState() {
        binding?.favoriteButton?.isVisible = false
        binding?.unFavoriteButton?.isVisible = true
    }

    override fun successDeleteState() {
        binding?.favoriteButton?.isVisible = true
        binding?.unFavoriteButton?.isVisible = false
    }

    override fun enlargeImage() {
        zoomPicture(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ImageView.ScaleType.CENTER_CROP
        )
    }

    override fun reduceImage() {
        zoomPicture(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ImageView.ScaleType.FIT_CENTER
        )
    }

    override fun showError(message: String?) {
        toast(message)
    }

    override fun setLoadingState() {
        binding?.progressBar?.isVisible = true
        binding?.webView?.isVisible = false
        binding?.imageView?.isVisible = false
    }

    override fun setNormalState() {
        binding?.progressBar?.isVisible = false
    }

    private fun zoomPicture(paramsHeight: Int, scaleType: ImageView.ScaleType) {
        binding?.root?.beginDelayedTransition()
        binding?.imageView?.layoutParams?.height = paramsHeight
        binding?.frameLayoutContainer?.layoutParams?.height = paramsHeight
        binding?.imageView?.scaleType = scaleType
    }

    override fun showImage(url: String) {
        binding?.webView?.isVisible = false
        binding?.imageView?.isVisible = true
        binding?.imageView?.setImageFromUri(url)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun showVideo(url: String) {
        binding?.apply {
            imageView.isVisible = false
            (webView as WebView).apply {
                isVisible = true
                settings.javaScriptEnabled = true
                loadUrl(url)
            }
        }
    }

    override fun onDetach() {
        binding = null
        super.onDetach()
    }

    companion object {
        private const val REQUIRE_DATE = "REQUIRE_DATE"

        fun newInstance(date: String): Fragment =
            PictureOfTheDayFragment().apply {
                arguments = Bundle().apply { putString(REQUIRE_DATE, date) }
            }
    }
}