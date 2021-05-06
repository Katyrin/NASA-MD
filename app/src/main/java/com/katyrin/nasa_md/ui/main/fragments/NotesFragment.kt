package com.katyrin.nasa_md.ui.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.katyrin.nasa_md.R
import com.katyrin.nasa_md.databinding.NotesFragmentBinding
import com.katyrin.nasa_md.ui.main.fragments.adapters.notes.NotesRecyclerView
import com.katyrin.nasa_md.ui.main.model.data.Note
import com.katyrin.nasa_md.ui.main.picture.AppState
import com.katyrin.nasa_md.ui.main.picture.NotesViewModel

class NotesFragment : Fragment() {

    companion object {
        fun newInstance() = NotesFragment()
    }

    private lateinit var binding: NotesFragmentBinding
    private val viewModel: NotesViewModel by lazy {
        ViewModelProvider(this).get(NotesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = NotesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.liveData.observe(viewLifecycleOwner) { renderData(it) }
        viewModel.getAllNotes()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.SuccessLocalQuery -> {
                binding.progressBar.visibility = View.GONE
                binding.notesRecyclerView.visibility = View.VISIBLE
                if (appState.notes.isEmpty()) {
                    initRecyclerView(viewModel.getFakeNotes())
                    viewModel.getFakeNotes().map {
                        viewModel.saveNoteToDB(it)
                    }
                } else {
                    initRecyclerView(appState.notes)
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.success_local_db),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            is AppState.Loading -> {
                binding.notesRecyclerView.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(requireContext(), getString(R.string.error), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun initRecyclerView(notes: List<Note>) {
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.notesRecyclerView.layoutManager = layoutManager
        binding.notesRecyclerView.adapter = NotesRecyclerView(notes) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        }
    }
}