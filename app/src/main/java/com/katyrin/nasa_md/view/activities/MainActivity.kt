package com.katyrin.nasa_md.view.activities

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomappbar.BottomAppBar
import com.katyrin.nasa_md.R
import com.katyrin.nasa_md.databinding.MainActivityBinding
import com.katyrin.nasa_md.ui.main.fragments.BottomNavigationDrawerFragment
import com.katyrin.nasa_md.ui.main.fragments.NewNoteFragment
import com.katyrin.nasa_md.ui.main.fragments.NotesFragment
import com.katyrin.nasa_md.view.HomeFragment
import com.katyrin.nasa_md.view.SettingsFragment
import com.katyrin.nasa_md.view.abs.AbsActivity
import com.katyrin.nasa_md.view.viewpager.ViewPagerFragment

class MainActivity : AbsActivity(R.layout.main_activity) {

    private lateinit var binding: MainActivityBinding
    private var viewPagerFragment: Fragment? = null
    private val noteFragment: NotesFragment by lazy { NotesFragment.newInstance() }
    private val newNoteFragment: NewNoteFragment by lazy { NewNoteFragment.newInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setBottomBar()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, HomeFragment.newInstance())
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
        when (item.itemId) {
            R.id.app_bar_main -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, HomeFragment.newInstance())
                    .commitNow()
                setMainFabPosition()
            }
            R.id.app_bar_notes -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, noteFragment)
                    .commitAllowingStateLoss()
                setMainFabPosition(R.drawable.ic_plus_fab)
            }
            R.id.app_bar_settings -> {
                supportFragmentManager.apply {
                    beginTransaction()
                        .replace(R.id.container, SettingsFragment.newInstance())
                        .commitAllowingStateLoss()
                    setMainFabPosition()
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
        fabBackPress()
        super.onBackPressed()
    }

    private fun fabBackPress() {
        val fragment = supportFragmentManager.findFragmentById(R.id.container)
        if (fragment is NewNoteFragment) {
            setMainFabPosition(R.drawable.ic_plus_fab)
        } else {
            setMainFabPosition()
        }
    }

    private fun fabInitActivity() {
        val fragment = supportFragmentManager.findFragmentById(R.id.container)
        if (fragment is NotesFragment) {
            setMainFabPosition(R.drawable.ic_plus_fab)
        } else {
            setMainFabPosition()
        }
    }

    private fun setBottomBar() {
        setSupportActionBar(binding.bottomAppBar)

        if (isMain) {
            fabInitActivity()
        } else {
            setNotMainFabPosition()
        }

        binding.fab.setOnClickListener {
            val fragment = supportFragmentManager.findFragmentById(R.id.container)
            if (isMain) {
                setNotMainFabPosition()
                if (fragment is NotesFragment) {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, newNoteFragment)
                        .addToBackStack(NEW_NOTE_FRAGMENT)
                        .commit()
                } else {
                    viewPagerFragment = ViewPagerFragment.newInstance()
                    supportFragmentManager.beginTransaction()
                        .add(R.id.container, viewPagerFragment!!)
                        .addToBackStack(VIEW_PAGER_FRAGMENT)
                        .commitAllowingStateLoss()
                }
            } else {
                fabBackPress()
                supportFragmentManager.popBackStack()
                viewPagerFragment = null
            }
        }
    }

    private fun setMainFabPosition(icon: Int = R.drawable.ic_star) {
        setFABPosition(
            true,
            ContextCompat.getDrawable(this, R.drawable.ic_hamburger_menu_bottom_bar),
            BottomAppBar.FAB_ALIGNMENT_MODE_CENTER, icon, R.menu.menu_bottom_bar
        )
    }

    private fun setNotMainFabPosition() {
        setFABPosition(
            false, null,
            BottomAppBar.FAB_ALIGNMENT_MODE_END, R.drawable.ic_back_fab,
            R.menu.menu_bottom_bar_other_screen
        )
    }

    private fun setFABPosition(
        isMainScreen: Boolean,
        navIcon: Drawable?,
        position: Int,
        fabImage: Int,
        menu: Int
    ) {
        isMain = isMainScreen
        binding.bottomAppBar.apply {
            navigationIcon = navIcon
            fabAlignmentMode = position
            replaceMenu(menu)
        }
        binding.fab.setImageDrawable(ContextCompat.getDrawable(this, fabImage))
    }

    companion object {
        private const val VIEW_PAGER_FRAGMENT = "VIEW_PAGER_FRAGMENT"
        private const val NEW_NOTE_FRAGMENT = "NEW_NOTE_FRAGMENT"
        private var isMain = true
    }
}