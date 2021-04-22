package com.katyrin.nasa_md

import android.content.Context
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat.recreate
import androidx.fragment.app.Fragment
import com.katyrin.nasa_md.databinding.FragmentSettingsBinding

const val SETTINGS_SHARED_PREFERENCE = "SETTINGS_SHARED_PREFERENCE"
const val THEME_NAME_SHARED_PREFERENCE = "THEME_NAME_SHARED_PREFERENCE"
const val THEME_RES_ID = "THEME_RES_ID"
const val SPACE = "SPACE"
const val MARS = "MARS"
const val MOON = "MOON"

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private lateinit var themeName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setSharedPreferenceSettings()
        binding.marsTheme.apply {
            setOnClickListener {
                if (themeName != MARS) {
                    requireContext().apply {
                        //requireActivity().theme.applyStyle(R.style.Theme_NASAMD_Mars, true)
                        setTheme(R.style.Theme_NASAMD_Mars)
                        recreate(requireActivity())
                        Toast.makeText(this, "Mars", Toast.LENGTH_SHORT).show()
                        saveThemeSettings(MARS, R.style.Theme_NASAMD_Mars)
                    }
                }
            }
        }
        binding.spaceTheme.apply {
            setOnClickListener {
                if (themeName != SPACE) {
                    requireActivity().apply {
                        setTheme(R.style.Theme_NASAMD)
                        recreate()
                        Toast.makeText(this, "Space", Toast.LENGTH_SHORT).show()
                        saveThemeSettings(SPACE, R.style.Theme_NASAMD)
                    }
                }
            }
        }
        binding.moonTheme.apply {
            setOnClickListener {
                if (themeName != MOON) {
                    requireActivity().apply {
                        setTheme(R.style.Theme_NASAMD_Moon)
                        recreate()
                        Toast.makeText(this, "Moon", Toast.LENGTH_SHORT).show()
                        saveThemeSettings(MOON, R.style.Theme_NASAMD_Moon)
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val resIdTheme =
            requireActivity().getSharedPreferences(SETTINGS_SHARED_PREFERENCE, Context.MODE_PRIVATE)
                .getInt(THEME_RES_ID, R.style.Theme_NASAMD)
        val inflaterNewTheme = LayoutInflater.from(ContextThemeWrapper(context, resIdTheme))
        binding = FragmentSettingsBinding.inflate(inflaterNewTheme, container, false)
        return binding.root
    }

    private fun saveThemeSettings(themeName: String, id: Int) {
        this.themeName = themeName
        activity?.let {
            with(it.getSharedPreferences(SETTINGS_SHARED_PREFERENCE, Context.MODE_PRIVATE).edit()) {
                putString(THEME_NAME_SHARED_PREFERENCE, themeName).apply()
                putInt(THEME_RES_ID, id).apply()
            }
        }
    }

    private fun setSharedPreferenceSettings() {
        activity?.let {
            themeName =
                it.getSharedPreferences(SETTINGS_SHARED_PREFERENCE, Context.MODE_PRIVATE)
                    .getString(THEME_NAME_SHARED_PREFERENCE, SPACE).toString()
            when(themeName) {
                MOON -> {
                    binding.moonTheme.isChecked = true
                }
                MARS -> {
                    binding.marsTheme.isChecked = true
                }
                else -> {
                    binding.spaceTheme.isChecked = true
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SettingsFragment()
    }
}