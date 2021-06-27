package com.katyrin.nasa_md.presenter.settings

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(OneExecutionStateStrategy::class)
interface SettingsView : MvpView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setChipsClickListener()
    fun showError(message: String?)
    fun recreateActivity()
    fun selectChipByTheme(themeId: Int)
}