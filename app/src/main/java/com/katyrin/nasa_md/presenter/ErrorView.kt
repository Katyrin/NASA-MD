package com.katyrin.nasa_md.presenter

import moxy.MvpView
import moxy.viewstate.strategy.alias.Skip

interface ErrorView : MvpView {
    @Skip
    fun showError(message: String?)
}