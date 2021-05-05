package com.katyrin.nasa_md.ui.main.fragments

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomappbar.BottomAppBar
import com.katyrin.nasa_md.R
import com.katyrin.nasa_md.databinding.MainActivityBinding
import com.katyrin.nasa_md.ui.main.fragments.adapters.DotsRecyclerViewAdapter
import com.katyrin.nasa_md.ui.main.picture.BottomNavigationDrawerFragment


private const val SETTINGS_FRAGMENT = "SETTINGS_FRAGMENT"
private const val NOTES_FRAGMENT = "NOTES_FRAGMENT"
private const val VIEW_PAGER_FRAGMENT = "VIEW_PAGER_FRAGMENT"
private const val NEW_NOTE_FRAGMENT = "NEW_NOTE_FRAGMENT"
private const val SIZE_PAGES = 10

class MainActivity : AppCompatActivity(), OnPositionListener {

    companion object{
        private var isMain = true
        private var savePosition = 0
    }

    private val adapter: DotsRecyclerViewAdapter by lazy { DotsRecyclerViewAdapter(SIZE_PAGES, this) }
    private lateinit var binding: MainActivityBinding
    private var viewPagerFragment: ViewPagerFragment? = null
    private val layoutManager: LinearLayoutManager by lazy { LinearLayoutManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {

        val resIdTheme = getSharedPreferences(SETTINGS_SHARED_PREFERENCE, Context.MODE_PRIVATE)
            .getInt(THEME_RES_ID, R.style.Theme_NASAMD)
        setTheme(resIdTheme)

        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)

        initRecyclerView()
        setContentView(binding.root)
        setBottomBar()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, PictureOfTheDayFragment.newInstance())
                    .commitNow()
        }
    }

    private fun initRecyclerView() {
        adapter.dotPosition = savePosition
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.dotsRecyclerView.layoutManager = layoutManager
        binding.dotsRecyclerView.adapter = adapter
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
            R.id.app_bar_notes -> {
                supportFragmentManager.beginTransaction()
                    .add(R.id.container, NotesFragment.newInstance())
                    .addToBackStack(NOTES_FRAGMENT)
                    .commitAllowingStateLoss()
            }
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
            val fragment = supportFragmentManager.findFragmentById(R.id.container)
            if (fragment is ViewPagerFragment) {
                setFABPosition(false, null,
                    BottomAppBar.FAB_ALIGNMENT_MODE_END, R.drawable.ic_back_fab,
                    R.menu.menu_bottom_bar_other_screen)
            } else {
                setFABPosition(false, null,
                    BottomAppBar.FAB_ALIGNMENT_MODE_END, R.drawable.ic_save,
                    R.menu.menu_bottom_bar_other_screen)
            }
        }

        binding.fab.setOnClickListener {
            val fragment = supportFragmentManager.findFragmentById(R.id.container)
            if (isMain) {
                if (fragment is NotesFragment) {
                    setFABPosition(false, null,
                        BottomAppBar.FAB_ALIGNMENT_MODE_END, R.drawable.ic_save,
                        R.menu.menu_bottom_bar_other_screen)

                    supportFragmentManager.beginTransaction()
                        .add(R.id.container, NewNoteFragment.newInstance())
                        .addToBackStack(NEW_NOTE_FRAGMENT)
                        .commitAllowingStateLoss()
                } else {
                    setFABPosition(false, null,
                        BottomAppBar.FAB_ALIGNMENT_MODE_END, R.drawable.ic_back_fab,
                        R.menu.menu_bottom_bar_other_screen)

                    viewPagerFragment = ViewPagerFragment.newInstance()
                    supportFragmentManager.beginTransaction()
                        .add(R.id.container, viewPagerFragment!!)
                        .addToBackStack(VIEW_PAGER_FRAGMENT)
                        .commitAllowingStateLoss()
                }
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
        val fragment = supportFragmentManager.findFragmentById(R.id.container)
        isMain = isMainScreen
        if (fragment is PictureOfTheDayFragment && !isMain) {
            binding.dotsRecyclerView.visibility = View.VISIBLE
        } else {
            binding.dotsRecyclerView.visibility = View.GONE
        }
        binding.bottomAppBar.apply {
            navigationIcon = navIcon
            fabAlignmentMode = position
            replaceMenu(menu)
        }
        binding.fab.setImageDrawable(ContextCompat.getDrawable(this, fabImage))
    }

    override fun setDotsColor(position: Int) {
        savePosition = position
        val drawablePassive: Drawable? =
            ContextCompat.getDrawable(this, R.drawable.swipe_indicator_passive)
        val drawableActive: Drawable? =
            ContextCompat.getDrawable(this, R.drawable.swipe_indicator_active)
        for (i in 0 until SIZE_PAGES) {
            val item = layoutManager.findViewByPosition(i) as? ImageView
            if (position == i) {
                item?.setImageDrawable(drawableActive)
            } else {
                item?.setImageDrawable(drawablePassive)
            }
        }
    }
}