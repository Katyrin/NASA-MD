package com.katyrin.nasa_md.presenter.settings

import com.katyrin.nasa_md.presenter.ErrorView
import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import moxy.viewstate.strategy.alias.AddToEndSingle

@StateStrategyType(OneExecutionStateStrategy::class)
interface SettingsView : ErrorView, MvpView {
    @AddToEndSingle
    fun setChipsClickListener()
    fun recreateActivity()
    fun selectChipByTheme(themeId: Int)
}