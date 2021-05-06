package com.katyrin.nasa_md.ui.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.katyrin.nasa_md.databinding.FragmentNewNoteBinding
import com.katyrin.nasa_md.ui.main.model.data.Note
import com.katyrin.nasa_md.ui.main.picture.NotesViewModel


class NewNoteFragment : Fragment(), OnBackStackInterface {

    private lateinit var binding: FragmentNewNoteBinding
    private val viewModel: NotesViewModel by lazy {
        ViewModelProvider(this).get(NotesViewModel::class.java)
    }

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
        val newNote = Note(
            header = binding.headerInputEditText.text.toString(),
            description = binding.noteInputEditText.text.toString(),
            isImportant = binding.isImportantChip.isChecked
        )
        viewModel.saveNoteToDB(newNote)
    }
}