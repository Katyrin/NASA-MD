package com.katyrin.nasa_md.presenter.viewpager

import moxy.MvpPresenter

class ViewPagerPresenter: MvpPresenter<ViewPagerView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
    }
}