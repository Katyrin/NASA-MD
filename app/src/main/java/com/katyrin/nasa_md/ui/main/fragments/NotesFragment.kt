package com.katyrin.nasa_md.ui.main.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.katyrin.nasa_md.R
import com.katyrin.nasa_md.databinding.NotesFragmentBinding
import com.katyrin.nasa_md.ui.main.fragments.adapters.notes.ItemTouchHelperCallback
import com.katyrin.nasa_md.ui.main.fragments.adapters.notes.NotesRecyclerViewAdapter
import com.katyrin.nasa_md.ui.main.model.data.Note
import com.katyrin.nasa_md.ui.main.picture.AppState
import com.katyrin.nasa_md.ui.main.picture.NotesViewModel

class NotesFragment : Fragment() {

    companion object {
        fun newInstance() = NotesFragment()
    }

    private lateinit var itemTouchHelper: ItemTouchHelper
    private var newNote: Note? = null
    private lateinit var allNotes: MutableList<Note>
    private lateinit var binding: NotesFragmentBinding
    private val viewModel: NotesViewModel by lazy {
        ViewModelProvider(this).get(NotesViewModel::class.java)
    }
    private val adapter: NotesRecyclerViewAdapter by lazy {
        NotesRecyclerViewAdapter(
            { Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show() },
            { viewModel.deleteNoteToDB(it) },
            { itemTouchHelper.startDrag(it) }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireActivity()
            .getSharedPreferences(NEW_NOTE_SHARED_PREFERENCES, Context.MODE_PRIVATE).apply {
                newNote = Note(
                    getString(NEW_NOTE_HEADER, "")!!,
                    getString(NEW_NOTE_DESCRIPTION, "")!!,
                    getBoolean(NEW_NOTE_IS_IMPORTANT, false)
                )
            }
        requireActivity().getSharedPreferences(NEW_NOTE_SHARED_PREFERENCES, Context.MODE_PRIVATE).edit().apply {
            putString(NEW_NOTE_HEADER, "")
            putString(NEW_NOTE_DESCRIPTION, "")
            putBoolean(NEW_NOTE_IS_IMPORTANT, false)
        }.apply()
        binding = NotesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.liveData.observe(viewLifecycleOwner) { renderData(it) }
        viewModel.getAllNotes()

        searchNotes()
    }

    private fun searchNotes() {
        binding.apply {
            searchButton.setOnClickListener {
                val text = inputEditText.text.toString()
                val isImportant = importantChip.isChecked
                val currentList: MutableList<Note> = mutableListOf()

                allNotes.map {
                    if (it.header.contains(text, true)) {
                        if (isImportant) {
                            if (it.isImportant)
                                currentList.add(it)
                        } else {
                            currentList.add(it)
                        }
                    }
                }

                initRecyclerView(currentList)
            }
        }
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.SuccessLocalQuery -> {
                allNotes = mutableListOf()
                allNotes = appState.notes.toMutableList()
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
        adapter.setData(notes.toMutableList())

        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.notesRecyclerView.layoutManager = layoutManager
        binding.notesRecyclerView.adapter = adapter

        itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(binding.notesRecyclerView)

        addNewItem()
    }

    private fun addNewItem() {
        val note: Note = newNote ?: Note()
        if (note.header.isNotEmpty()) {
            adapter.appendItem(newNote!!)
            viewModel.saveNoteToDB(note)
            allNotes.add(note)
            newNote = null
        }
    }
}