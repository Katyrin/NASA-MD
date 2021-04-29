package com.katyrin.nasa_md.ui.main.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.katyrin.nasa_md.R
import com.katyrin.nasa_md.databinding.FragmentPictureOfTheDateBinding
import com.katyrin.nasa_md.ui.main.picture.PictureOfTheDayData
import com.katyrin.nasa_md.ui.main.picture.PictureOfTheDayViewModel

private const val REQUIRE_DATE = "REQUIRE_DATE"

class PictureOfTheDateFragment : Fragment() {

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
                binding.webView.isVisible = true
                binding.imageView.isVisible = false
                binding.webView.settings.javaScriptEnabled = true
                binding.webView.loadUrl(url)
            }
        }
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