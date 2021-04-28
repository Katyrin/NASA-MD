package com.katyrin.nasa_md.ui.main.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.katyrin.nasa_md.databinding.FragmentViewPagerBinding
import com.katyrin.nasa_md.ui.main.fragments.adapters.ViewPagerAdapter
import java.text.SimpleDateFormat
import java.util.*

class ViewPagerFragment : Fragment(), OnBackStackInterface {

    private val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private lateinit var binding: FragmentViewPagerBinding
    private val fragments: MutableList<Fragment> = mutableListOf()
    private val viewPagerAdapter: ViewPagerAdapter by lazy {
        ViewPagerAdapter(requireActivity().supportFragmentManager, fragments)
    }
    private var onPositionListener: OnPositionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewPager.adapter = viewPagerAdapter
        binding.viewPager.currentItem = position

        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {
                onPositionListener?.setDotsColor(position)
            }
            override fun onPageScrollStateChanged(state: Int) {}

        })
    }

    override fun onStart() {
        super.onStart()
        onPositionListener?.setDotsColor(position)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onPositionListener = context as MainActivity
    }

    override fun onDetach() {
        super.onDetach()
        position = binding.viewPager.currentItem
        onPositionListener = null
    }

    private fun initListFragment(){
        fragments.clear()
        for (i in 0..9) {
            fragments.add(PictureOfTheDateFragment.newInstance(formatter.format(previousDay(i))))
        }
    }

    private fun previousDay(daysAgo: Int): Date {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DATE, -daysAgo)
        return cal.time
    }

    companion object {
        @JvmStatic
        fun newInstance(): ViewPagerFragment {
           return ViewPagerFragment()
        }
        private var position: Int = 0
    }
}