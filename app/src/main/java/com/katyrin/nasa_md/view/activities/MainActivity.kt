package com.katyrin.nasa_md.view.activities

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.google.android.material.bottomappbar.BottomAppBar
import com.katyrin.nasa_md.R
import com.katyrin.nasa_md.databinding.MainActivityBinding
import com.katyrin.nasa_md.presenter.main.MainPresenter
import com.katyrin.nasa_md.presenter.main.MainView
import com.katyrin.nasa_md.view.abs.AbsActivity
import com.katyrin.nasa_md.view.favorites.FavoritesScreen
import com.katyrin.nasa_md.view.findsatellitephoto.FindSatellitePhotoScreen
import com.katyrin.nasa_md.view.home.HomeScreen
import com.katyrin.nasa_md.view.settings.SettingsScreen
import com.katyrin.nasa_md.view.viewpager.ViewPagerScreen
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class MainActivity : AbsActivity(R.layout.main_activity), MainView {

    @Inject
    @InjectPresenter
    lateinit var presenter: MainPresenter

    @ProvidePresenter
    fun providePresenter(): MainPresenter = presenter

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    private val navigator = AppNavigator(activity = this, R.id.container)
    private var binding: MainActivityBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setBottomBar()
    }

    override fun init() {
        initBottomAppBar()
    }

    private fun initBottomAppBar() {
        binding?.bottomAppBar?.setNavigationOnClickListener {
            presenter.replaceScreen(FindSatellitePhotoScreen)
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
            R.id.app_bar_main -> presenter.replaceScreen(HomeScreen)
            R.id.app_bar_favorites -> presenter.replaceScreen(FavoritesScreen)
            R.id.app_bar_settings -> presenter.replaceScreen(SettingsScreen)
            R.id.app_bar_search -> Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show()
        }
        return true
    }

    override fun onBackPressed() {
        setMainFabPosition()
        super.onBackPressed()
    }

    private fun setBottomBar() {
        setSupportActionBar(binding?.bottomAppBar)

        if (isMain) setMainFabPosition()
        else setNotMainFabPosition()

        setFabClickListener()
    }

    private fun setFabClickListener() {
        binding?.fab?.setOnClickListener {
            if (isMain) {
                setNotMainFabPosition()
                presenter.navigateToScreen(ViewPagerScreen)
            } else {
                setMainFabPosition()
                presenter.backPreviousScreen()
            }
        }
    }

    private fun setMainFabPosition() {
        setFAB(
            ContextCompat.getDrawable(this, R.drawable.ic_search),
            BottomAppBar.FAB_ALIGNMENT_MODE_CENTER,
            R.drawable.ic_star,
            R.menu.menu_bottom_bar
        )
        isMain = true
    }

    private fun setNotMainFabPosition() {
        setFAB(
            null,
            BottomAppBar.FAB_ALIGNMENT_MODE_END,
            R.drawable.ic_back_fab,
            R.menu.menu_bottom_bar_other_screen
        )
        isMain = false
    }

    private fun setFAB(navIcon: Drawable?, position: Int, fabImage: Int, menu: Int) {
        binding?.bottomAppBar?.apply {
            navigationIcon = navIcon
            fabAlignmentMode = position
            replaceMenu(menu)
        }
        binding?.fab?.setImageDrawable(ContextCompat.getDrawable(this, fabImage))
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

    companion object {
        private var isMain = true
    }
}