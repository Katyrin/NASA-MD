package com.katyrin.nasa_md.presenter.main

import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.katyrin.nasa_md.view.home.HomeScreen
import moxy.MvpPresenter
import javax.inject.Inject

class MainPresenter @Inject constructor(
    private val router: Router
) : MvpPresenter<MainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        replaceScreen(HomeScreen)
        viewState.init()
    }

    fun replaceScreen(screen: FragmentScreen) {
        router.newRootScreen(screen)
    }

    fun navigateToScreen(screen: FragmentScreen) {
        router.navigateTo(screen)
    }

    fun backPreviousScreen() {
        router.exit()
    }
}