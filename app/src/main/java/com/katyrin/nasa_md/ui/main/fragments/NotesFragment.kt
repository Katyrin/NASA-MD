package com.katyrin.nasa_md.ui.main.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.katyrin.nasa_md.R
import com.katyrin.nasa_md.databinding.NotesFragmentBinding
import com.katyrin.nasa_md.ui.main.fragments.adapters.notes.NotesRecyclerView
import com.katyrin.nasa_md.ui.main.picture.NotesViewModel

class NotesFragment : Fragment() {

    companion object {
        fun newInstance() = NotesFragment()
    }

    private lateinit var binding: NotesFragmentBinding
    private lateinit var viewModel: NotesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = NotesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NotesViewModel::class.java)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.notesRecyclerView.layoutManager = layoutManager
        binding.notesRecyclerView.adapter = NotesRecyclerView(viewModel.getNotes()) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        }
    }

}