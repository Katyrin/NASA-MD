package com.katyrin.nasa_md.ui.main.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import android.webkit.WebView
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import coil.load
import com.katyrin.nasa_md.R
import com.katyrin.nasa_md.databinding.FragmentPictureOfTheDateBinding
import com.katyrin.nasa_md.ui.main.viewmodel.PictureOfTheDayData
import com.katyrin.nasa_md.ui.main.viewmodel.PictureOfTheDayViewModel


private const val REQUIRE_DATE = "REQUIRE_DATE"

class PictureOfTheDateFragment : Fragment() {

    private var isExpanded = false
    private var date: String? = null
    private lateinit var binding: FragmentPictureOfTheDateBinding
    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            date = it.getString(REQUIRE_DATE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPictureOfTheDateBinding.inflate(inflater, container, false)
        binding.dateTextView.text = date
        initDoubleClickOnImage()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getData(date).observe(this, { renderData(it) })
    }

    private fun renderData(data: PictureOfTheDayData) {
        when(data) {
            is PictureOfTheDayData.Success -> {
                handlingSuccessRequest(data)
            }

            is PictureOfTheDayData.Loading -> {
                binding.progressBar.visibility = View.VISIBLE
            }

            is PictureOfTheDayData.Error -> {
                binding.progressBar.visibility = View.GONE
                toast(data.error.message)
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun handlingSuccessRequest(data: PictureOfTheDayData.Success) {
        binding.progressBar.visibility = View.GONE
        val serverResponseData = data.serverResponseData
        val url = serverResponseData.url
        if (url.isNullOrEmpty()) {
            toast("Link is empty")
        } else {
            val separateUrl = url.split('.')
            if (separateUrl[1] != "youtube") {
                binding.webView.isVisible = false
                binding.imageView.isVisible = true
                binding.imageView.load(url) {
                    lifecycle(this@PictureOfTheDateFragment)
                    error(R.drawable.ic_load_error_vector)
                    placeholder(R.drawable.ic_no_photo_vector)
                }

            } else {
                binding.apply {
                    imageView.isVisible = false
                    (webView as WebView).apply {
                        isVisible = true
                        settings.javaScriptEnabled = true
                        loadUrl(url)
                    }
                }
            }
        }
    }

    private fun initDoubleClickOnImage() {
        var doubleClick = false
        val handler = Handler(Looper.getMainLooper())
        val r = Runnable { doubleClick = false }
        binding.imageView.setOnClickListener {
            if (doubleClick) {
                zoomPicture()
                doubleClick = false
            } else {
                doubleClick = true
                handler.postDelayed(r, 500)
            }
        }
    }

    private fun zoomPicture() {
        isExpanded = !isExpanded
        TransitionManager.beginDelayedTransition(
            binding.root, TransitionSet()
                .addTransition(ChangeBounds())
                .addTransition(ChangeImageTransform())
        )

        val params: ViewGroup.LayoutParams = binding.imageView.layoutParams
        params.height = if (isExpanded)
            ViewGroup.LayoutParams.MATCH_PARENT
        else
            ViewGroup.LayoutParams.WRAP_CONTENT
        binding.imageView.layoutParams = params

        val frameParams: ViewGroup.LayoutParams = binding.frameLayoutContainer!!.layoutParams
        frameParams.height = if (isExpanded)
            ViewGroup.LayoutParams.MATCH_PARENT
        else
            ViewGroup.LayoutParams.WRAP_CONTENT
        binding.frameLayoutContainer!!.layoutParams = frameParams

        binding.imageView.scaleType = if (isExpanded)
            ImageView.ScaleType.CENTER_CROP
        else
            ImageView.ScaleType.FIT_CENTER
    }

    private fun Fragment.toast(string: String?) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
            show()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(date: String) =
            PictureOfTheDateFragment().apply {
                arguments = Bundle().apply {
                    putString(REQUIRE_DATE, date)
                }
            }
    }
}