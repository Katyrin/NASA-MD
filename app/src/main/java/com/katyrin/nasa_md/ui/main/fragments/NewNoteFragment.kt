package com.katyrin.nasa_md.ui.main.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.katyrin.nasa_md.databinding.FragmentNewNoteBinding

const val NEW_NOTE_SHARED_PREFERENCES = "NEW_NOTE_SHARED_PREFERENCES"
const val NEW_NOTE_HEADER = "NEW_NOTE_HEADER"
const val NEW_NOTE_DESCRIPTION = "NEW_NOTE_DESCRIPTION"
const val NEW_NOTE_IS_IMPORTANT = "NEW_NOTE_IS_IMPORTANT"

class NewNoteFragment : Fragment(), OnBackStackInterface {

    private lateinit var binding: FragmentNewNoteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.saveButton.setOnClickListener {
            onSaveData()
            requireActivity().onBackPressed()
        }
    }

    companion object {
        fun newInstance() = NewNoteFragment()
    }

    private fun onSaveData() {
        requireActivity()
            .getSharedPreferences(NEW_NOTE_SHARED_PREFERENCES, Context.MODE_PRIVATE).edit().apply {
                putString(NEW_NOTE_HEADER, binding.headerInputEditText.text.toString())
                putString(NEW_NOTE_DESCRIPTION, binding.noteInputEditText.text.toString())
                putBoolean(NEW_NOTE_IS_IMPORTANT, binding.isImportantChip.isChecked)
        }.apply()
    }
}