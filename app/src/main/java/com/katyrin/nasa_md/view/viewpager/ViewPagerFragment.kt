package com.katyrin.nasa_md.view.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.katyrin.nasa_md.R
import com.katyrin.nasa_md.databinding.FragmentViewPagerBinding
import com.katyrin.nasa_md.presenter.viewpager.ViewPagerPresenter
import com.katyrin.nasa_md.presenter.viewpager.ViewPagerView
import com.katyrin.nasa_md.ui.main.fragments.PictureOfTheDateFragment
import com.katyrin.nasa_md.utils.previousDay
import com.katyrin.nasa_md.view.abs.AbsFragment
import moxy.ktx.moxyPresenter

class ViewPagerFragment : AbsFragment(R.layout.fragment_view_pager), ViewPagerView {

    private var binding: FragmentViewPagerBinding? = null
    private var fragments: MutableList<Fragment>? = mutableListOf()
    private val presenter: ViewPagerPresenter by moxyPresenter { ViewPagerPresenter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentViewPagerBinding.inflate(inflater, container, false)
        .also { binding = it }.root

    override fun init() {
        initListFragment()
        binding?.viewPager?.adapter = fragments?.let { ViewPagerAdapter(requireActivity(), it) }
        binding?.indicator?.setViewPager(binding?.viewPager)
    }

    private fun initListFragment() {
        fragments?.clear()
        for (i in 0..9) {
            fragments?.add(PictureOfTheDateFragment.newInstance(i.previousDay()))
        }
    }

    override fun onDestroy() {
        binding = null
        fragments = null
        super.onDestroy()
    }

    companion object {
        fun newInstance(): ViewPagerFragment = ViewPagerFragment()
    }
}