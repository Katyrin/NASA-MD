package com.katyrin.nasa_md.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jakewharton.rxbinding4.view.clicks
import com.katyrin.nasa_md.R
import com.katyrin.nasa_md.databinding.FragmentSettingsBinding
import com.katyrin.nasa_md.model.data.Theme
import com.katyrin.nasa_md.presenter.settings.SettingsPresenter
import com.katyrin.nasa_md.presenter.settings.SettingsView
import com.katyrin.nasa_md.utils.toast
import com.katyrin.nasa_md.view.abs.AbsFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class SettingsFragment : AbsFragment(R.layout.fragment_settings), SettingsView {

    @Inject
    @InjectPresenter
    lateinit var presenter: SettingsPresenter

    @ProvidePresenter
    fun providePresenter(): SettingsPresenter = presenter

    private var binding: FragmentSettingsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentSettingsBinding.inflate(inflater, container, false)
        .also { binding = it }.root

    override fun showError(message: String?) {
        toast(message)
    }

    override fun recreateActivity() {
        requireActivity().recreate()
    }

    override fun setChipsClickListener() {
        binding?.marsTheme
            ?.clicks()
            ?.map { Theme.MARS }
            ?.let { presenter.subscribeSetTheme(it) }
        binding?.spaceTheme
            ?.clicks()
            ?.map { Theme.SPACE }
            ?.let { presenter.subscribeSetTheme(it) }
        binding?.moonTheme
            ?.clicks()
            ?.map { Theme.MOON }
            ?.let { presenter.subscribeSetTheme(it) }
    }

    override fun selectChipByTheme(themeId: Int) {
        when (themeId) {
            R.style.Theme_NASAMD_Moon -> binding?.moonTheme?.isChecked = true
            R.style.Theme_NASAMD_Mars -> binding?.marsTheme?.isChecked = true
            else -> binding?.spaceTheme?.isChecked = true
        }
    }

    override fun onDetach() {
        binding = null
        super.onDetach()
    }

    companion object {
        fun newInstance(): Fragment = SettingsFragment()
    }
}