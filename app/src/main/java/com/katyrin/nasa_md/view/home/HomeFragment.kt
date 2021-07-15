package com.katyrin.nasa_md.view.home

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.text.style.BulletSpan
import android.text.style.QuoteSpan
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import com.katyrin.nasa_md.R
import com.katyrin.nasa_md.databinding.FragmentHomeStartBinding
import com.katyrin.nasa_md.presenter.home.HomePresenter
import com.katyrin.nasa_md.presenter.home.HomeView
import com.katyrin.nasa_md.utils.getResIdFromAttribute
import com.katyrin.nasa_md.utils.previousDay
import com.katyrin.nasa_md.utils.setImageFromUri
import com.katyrin.nasa_md.utils.toast
import com.katyrin.nasa_md.view.abs.AbsFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject


class HomeFragment : AbsFragment(R.layout.fragment_home), HomeView {

    @Inject
    @InjectPresenter
    lateinit var presenter: HomePresenter

    @ProvidePresenter
    fun providePresenter(): HomePresenter = presenter

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private var binding: FragmentHomeStartBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentHomeStartBinding.inflate(inflater, container, false)
        .also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun init() {
        binding?.includeLayout?.bottomSheetContainer?.let { setBottomSheetBehavior(it) }
        binding?.inputLayout?.setEndIconOnClickListener { openWikipediaResult() }
        setSelectionChips()
    }

    private fun setSelectionChips() {
        binding?.chipGroup?.setOnCheckedChangeListener { group, checkedId ->
            group.findViewById<Chip>(checkedId)?.let { chip ->
                when (chip) {
                    binding?.today -> presenter.getData(null)
                    binding?.yesterday -> presenter.getData(ONE.previousDay())
                    binding?.dayBeforeYesterday -> presenter.getData(TWO.previousDay())
                }
            }
        }
    }

    override fun showLoadingState() {
        binding?.progressBar?.isVisible = true
        binding?.imageView?.isVisible = false
    }

    override fun showError(message: String?) {
        toast(message)
    }

    override fun showNormalState() {
        binding?.progressBar?.isVisible = false
        binding?.imageView?.isVisible = true
    }

    override fun showImage(url: String?) {
        binding?.imageView?.setImageFromUri(url)
    }

    override fun setBottomSheet(title: String, explanation: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            setTextSpan(title)
            setDescriptionSpan(explanation)
        } else {
            binding?.includeLayout?.bottomSheetDescriptionHeader?.text = title
            binding?.includeLayout?.bottomSheetDescription?.text = explanation
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun setTextSpan(text: String) {
        val spannable = SpannableString(text)
        spannable.setSpan(
            BulletSpan(
                TEXT_GAP_WIDTH,
                requireActivity().getResIdFromAttribute(),
                TEXT_BULLET_RADIUS
            ), TEXT_START, TEXT_END, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannable.setSpan(
            BackgroundColorSpan(requireActivity().getResIdFromAttribute()),
            TEXT_START,
            spannable.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding?.includeLayout?.bottomSheetDescriptionHeader?.text = spannable
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun setDescriptionSpan(text: String) {
        val spannable = SpannableString(text)
        spannable.setSpan(
            QuoteSpan(
                requireActivity().getResIdFromAttribute(),
                DESCRIPTION_STRIPE_WIDTH,
                DESCRIPTION_GAP_WIDTH
            ), TEXT_START, spannable.length - TEXT_END, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding?.includeLayout?.bottomSheetDescription?.text = spannable
    }

    private fun openWikipediaResult() {
        startActivity(Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(WIKI_BASE_URL + binding?.inputEditText?.text.toString())
        })
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    override fun startAnimation() {
        TransitionManager.beginDelayedTransition(
            binding?.constraintContainer,
            ChangeBounds().apply {
                interpolator = AnticipateOvershootInterpolator(INTERPOLATOR_TRANSITION)
                duration = ANIMATION_DURATION
            })

        ConstraintSet().apply {
            clone(requireContext(), R.layout.fragment_home)
            applyTo(binding?.constraintContainer)
        }
    }

    override fun onDetach() {
        binding = null
        super.onDetach()
    }

    companion object {
        private const val WIKI_BASE_URL = "https://en.wikipedia.org/wiki/"
        private const val INTERPOLATOR_TRANSITION = 1.0f
        private const val ANIMATION_DURATION = 1200L
        private const val ONE = 1
        private const val TWO = 2
        private const val TEXT_GAP_WIDTH = 20
        private const val TEXT_BULLET_RADIUS = 10
        private const val TEXT_START = 0
        private const val TEXT_END = 1
        private const val DESCRIPTION_STRIPE_WIDTH = 5
        private const val DESCRIPTION_GAP_WIDTH = 20

        fun newInstance(): Fragment = HomeFragment()
    }
}