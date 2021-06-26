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
import com.katyrin.nasa_md.view.PictureOfTheDayFragment
import com.katyrin.nasa_md.utils.previousDay
import com.katyrin.nasa_md.view.abs.AbsFragment
import moxy.ktx.moxyPresenter

class ViewPagerFragment : AbsFragment(R.layout.fragment_view_pager), ViewPagerView {

    private var binding: FragmentViewPagerBinding? = null
    private val presenter: ViewPagerPresenter by moxyPresenter { ViewPagerPresenter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentViewPagerBinding.inflate(inflater, container, false)
        .also { binding = it }.root

    override fun init() {
        binding?.viewPager?.adapter = ViewPagerAdapter(requireActivity(), getListFragment())
        binding?.indicator?.setViewPager(binding?.viewPager)
    }

    private fun getListFragment(): MutableList<Fragment> =
        MutableList(VIEW_PAGER_SIZE) { PictureOfTheDayFragment.newInstance(it.previousDay()) }

    override fun onDetach() {
        binding = null
        super.onDetach()
    }

    companion object {
        private const val VIEW_PAGER_SIZE = 10

        fun newInstance(): Fragment = ViewPagerFragment()
    }
}