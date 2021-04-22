package com.katyrin.nasa_md.ui.main.picture

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import com.katyrin.nasa_md.*
import com.katyrin.nasa_md.databinding.MainFragmentBinding
import java.text.SimpleDateFormat
import java.util.*

private const val SETTINGS_FRAGMENT = "SETTINGS_FRAGMENT"

class PictureOfTheDayFragment : Fragment() {

    companion object {
        fun newInstance() = PictureOfTheDayFragment()

        private var isMain = true
    }

    private val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private lateinit var binding: MainFragmentBinding
    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBottomSheetBehavior(binding.includeLayout.bottomSheetContainer)

        binding.inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(
                    "https://en.wikipedia.org/wiki/${binding.inputEditText.text.toString()}"
                )
            })
        }

        setBottomBar()
        setSelectionChips()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState == null)
            viewModel.getData(null).observe(this, { renderData(it) })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.app_bar_fav -> toast("Favorite")
            R.id.app_bar_settings -> {
                activity?.supportFragmentManager?.apply {
                    beginTransaction()
                        .add(R.id.container, SettingsFragment.newInstance())
                        .addToBackStack(SETTINGS_FRAGMENT)
                        .commitAllowingStateLoss()
                }
            }
            R.id.app_bar_search -> toast("Search")
            android.R.id.home -> {
                activity?.let {
                    BottomNavigationDrawerFragment()
                        .show(it.supportFragmentManager, "BottomNavigationDrawerFragment")
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setSelectionChips() {
        binding.chipGroup.setOnCheckedChangeListener { group, checkedId ->
            group.findViewById<Chip>(checkedId)?.let { chip ->
                when(chip) {
                    binding.today -> {
                        viewModel.getData(null).observe(this, { renderData(it) })
                    }
                    binding.yesterday -> {
                        viewModel.getData(formatter.format(previousDay(1)))
                            .observe(this, { renderData(it) })
                    }
                    binding.dayBeforeYesterday -> {
                        viewModel.getData(formatter.format(previousDay(2)))
                            .observe(this, { renderData(it) })
                    }
                }
            }
        }
    }

    private fun previousDay(daysAgo: Int): Date {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DATE, -daysAgo)
        return cal.time
    }

    private fun renderData(data: PictureOfTheDayData) {
        when(data) {
            is PictureOfTheDayData.Success -> {
                binding.progressBar.visibility = View.GONE
                val serverResponseData = data.serverResponseData
                val url = serverResponseData.url
                val title = serverResponseData.title
                val explanation = serverResponseData.explanation

                if (url.isNullOrEmpty()) {
                    toast("Сссылка пустая")
                } else {
                    binding.imageView.load(url) {
                        lifecycle(this@PictureOfTheDayFragment)
                        error(R.drawable.ic_load_error_vector)
                        placeholder(R.drawable.ic_no_photo_vector)
                    }
                }

                if (title.isNullOrEmpty()) {
                    binding.includeLayout.bottomSheetDescriptionHeader.text = ""
                } else {
                    binding.includeLayout.bottomSheetDescriptionHeader.text = title
                }

                if (explanation.isNullOrEmpty()) {
                    binding.includeLayout.bottomSheetDescription.text = "Статья отсутствует"
                } else {
                    binding.includeLayout.bottomSheetDescription.text = explanation
                }
            }

            is PictureOfTheDayData.Loading -> {
                binding.progressBar.visibility = View.VISIBLE
            }

            is PictureOfTheDayData.Error -> {
                binding.progressBar.visibility = View.GONE
                toast(data.error.message)
            }
        }
    }

    private fun Fragment.toast(string: String?) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
            show()
        }
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_DRAGGING -> toast("STATE_DRAGGING")
                    BottomSheetBehavior.STATE_COLLAPSED -> toast("STATE_COLLAPSED")
                    BottomSheetBehavior.STATE_EXPANDED -> toast("STATE_EXPANDED")
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> toast("STATE_HALF_EXPANDED")
                    BottomSheetBehavior.STATE_HIDDEN -> toast("STATE_HIDDEN")
                    BottomSheetBehavior.STATE_SETTLING -> toast("STATE_SETTLING")
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                toast("onSlide")
            }

        })
    }

    private fun setBottomBar() {
        val context = activity as MainActivity
        context.setSupportActionBar(binding.bottomAppBar)
        setHasOptionsMenu(true)

        binding.fab.setOnClickListener {
            if (isMain) {
                isMain = false
                binding.bottomAppBar.navigationIcon = null
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                binding.fab.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_back_fab
                    )
                )
                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar_other_screen)
            } else {
                isMain = true
                binding.bottomAppBar.navigationIcon =
                    ContextCompat.getDrawable(context, R.drawable.ic_hamburger_menu_bottom_bar)
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                binding.fab.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_plus_fab
                    )
                )
                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar)
            }
        }
    }
}