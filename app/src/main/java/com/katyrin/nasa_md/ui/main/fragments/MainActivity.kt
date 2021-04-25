package com.katyrin.nasa_md.ui.main.fragments

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.bottomappbar.BottomAppBar
import com.katyrin.nasa_md.R
import com.katyrin.nasa_md.databinding.MainActivityBinding
import com.katyrin.nasa_md.ui.main.fragments.*
import com.katyrin.nasa_md.ui.main.picture.BottomNavigationDrawerFragment


private const val SETTINGS_FRAGMENT = "SETTINGS_FRAGMENT"
private const val VIEW_PAGER_FRAGMENT = "VIEW_PAGER_FRAGMENT"

class MainActivity : AppCompatActivity(), OnPositionListener {

    companion object{
        private var isMain = true
    }

    private lateinit var binding: MainActivityBinding
    private var viewPagerFragment: ViewPagerFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)

        val resIdTheme = getSharedPreferences(SETTINGS_SHARED_PREFERENCE, Context.MODE_PRIVATE)
            .getInt(THEME_RES_ID, R.style.Theme_NASAMD)
        setTheme(resIdTheme)

        setContentView(binding.root)
        setBottomBar()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, PictureOfTheDayFragment.newInstance())
                    .commitNow()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (isMain)
            menuInflater.inflate(R.menu.menu_bottom_bar, menu)
        else
            menuInflater.inflate(R.menu.menu_bottom_bar_other_screen, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.app_bar_fav -> Toast.makeText(this, "Favorite", Toast.LENGTH_SHORT).show()
            R.id.app_bar_settings -> {
                supportFragmentManager.apply {
                    beginTransaction()
                        .add(R.id.container, SettingsFragment.newInstance())
                        .addToBackStack(SETTINGS_FRAGMENT)
                        .commitAllowingStateLoss()
                }
            }
            R.id.app_bar_search -> Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show()
            android.R.id.home -> {
                BottomNavigationDrawerFragment()
                    .show(supportFragmentManager, "BottomNavigationDrawerFragment")
            }
        }
        return true
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.container)
        if (fragment is OnBackStackInterface) {
            setFABPosition(true,
                ContextCompat.getDrawable(this, R.drawable.ic_hamburger_menu_bottom_bar),
                BottomAppBar.FAB_ALIGNMENT_MODE_CENTER, R.drawable.ic_plus_fab,
                R.menu.menu_bottom_bar)
        }
        super.onBackPressed()
    }

    private fun setBottomBar() {
        setSupportActionBar(binding.bottomAppBar)

        if (isMain) {
            setFABPosition(true,
                ContextCompat.getDrawable(this, R.drawable.ic_hamburger_menu_bottom_bar),
                BottomAppBar.FAB_ALIGNMENT_MODE_CENTER, R.drawable.ic_plus_fab,
                R.menu.menu_bottom_bar)
        } else {
            setFABPosition(false, null,
                BottomAppBar.FAB_ALIGNMENT_MODE_END, R.drawable.ic_back_fab,
                R.menu.menu_bottom_bar_other_screen)
        }

        binding.fab.setOnClickListener {
            if (isMain) {
                setFABPosition(false, null,
                    BottomAppBar.FAB_ALIGNMENT_MODE_END, R.drawable.ic_back_fab,
                    R.menu.menu_bottom_bar_other_screen)

                viewPagerFragment = ViewPagerFragment.newInstance()
                supportFragmentManager.beginTransaction()
                    .add(R.id.container, viewPagerFragment!!)
                    .addToBackStack(VIEW_PAGER_FRAGMENT)
                    .commitAllowingStateLoss()
            } else {
                setFABPosition(true,
                    ContextCompat.getDrawable(this, R.drawable.ic_hamburger_menu_bottom_bar),
                    BottomAppBar.FAB_ALIGNMENT_MODE_CENTER, R.drawable.ic_plus_fab,
                    R.menu.menu_bottom_bar)

                supportFragmentManager.popBackStack()
                viewPagerFragment = null
            }
        }
    }

    private fun setFABPosition(isMainScreen: Boolean,
                               navIcon: Drawable?,
                               position: Int,
                               fabImage: Int,
                               menu: Int) {
        isMain = isMainScreen
        if (isMain) {
            binding.dotsImageViews.visibility = View.GONE
        } else {
            binding.dotsImageViews.visibility = View.VISIBLE
        }
        binding.bottomAppBar.apply {
            navigationIcon = navIcon
            fabAlignmentMode = position
            replaceMenu(menu)
        }
        binding.fab.setImageDrawable(ContextCompat.getDrawable(this, fabImage))
    }

    override fun setDotsColor(position: Int) {
        val drawablePassive: Drawable? =
            ContextCompat.getDrawable(this, R.drawable.swipe_indicator_passive)
        val drawableActive: Drawable? =
            ContextCompat.getDrawable(this, R.drawable.swipe_indicator_active)
        val dots = listOf(binding.dot1, binding.dot2, binding.dot3, binding.dot4, binding.dot5,
            binding.dot6, binding.dot7, binding.dot8, binding.dot9, binding.dot10)
        dots.map {
            it.setImageDrawable(drawablePassive)
        }
        dots[position].setImageDrawable(drawableActive)
    }
}