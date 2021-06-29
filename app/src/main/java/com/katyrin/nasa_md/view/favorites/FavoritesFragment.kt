package com.katyrin.nasa_md.view.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import com.katyrin.nasa_md.R
import com.katyrin.nasa_md.databinding.FragmentFavoritesBinding
import com.katyrin.nasa_md.model.data.FavoriteContentEntity
import com.katyrin.nasa_md.presenter.favorites.FavoritesPresenter
import com.katyrin.nasa_md.presenter.favorites.FavoritesView
import com.katyrin.nasa_md.utils.toast
import com.katyrin.nasa_md.view.abs.AbsFragment
import com.katyrin.nasa_md.view.favorites.adapter.BaseViewHolder
import com.katyrin.nasa_md.view.favorites.adapter.FavoritesAdapter
import com.katyrin.nasa_md.view.favorites.adapter.ItemTouchHelperCallback
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class FavoritesFragment : AbsFragment(R.layout.fragment_favorites), FavoritesView,
    FavoritesAdapter.Delegate {

    @Inject
    @InjectPresenter
    lateinit var presenter: FavoritesPresenter

    @ProvidePresenter
    fun providePresenter(): FavoritesPresenter = presenter

    private var binding: FragmentFavoritesBinding? = null
    private var itemTouchHelper: ItemTouchHelper? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentFavoritesBinding.inflate(inflater, container, false)
        .also { binding = it }.root

    override fun init() {
        val adapter = FavoritesAdapter(this)
        binding?.favoritesRecyclerView?.adapter = adapter
        itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(adapter))
        itemTouchHelper?.attachToRecyclerView(binding?.favoritesRecyclerView)
    }

    override fun setLoadingState() {
        binding?.favoritesRecyclerView?.isVisible = false
        binding?.emptyText?.isVisible = false
        binding?.progressBar?.isVisible = true
    }

    override fun setNormalState() {
        binding?.favoritesRecyclerView?.isVisible = true
        binding?.emptyText?.isVisible = true
        binding?.progressBar?.isVisible = false
    }

    override fun showEmptyList() {
        binding?.favoritesRecyclerView?.isVisible = false
        binding?.emptyText?.isVisible = true
    }

    override fun showFavoritesList(listFavorites: List<FavoriteContentEntity>) {
        binding?.favoritesRecyclerView?.isVisible = true
        binding?.emptyText?.isVisible = false
        (binding?.favoritesRecyclerView?.adapter as FavoritesAdapter).submitList(listFavorites)
    }

    override fun showErrorMessage(message: String?) {
        toast(message)
    }

    override fun onFavoritePicked(favoriteContentEntity: FavoriteContentEntity) {
        requireActivity().supportFragmentManager.beginTransaction()
            .add(R.id.container, FavoriteContentFragment.newInstance(favoriteContentEntity))
            .addToBackStack(null)
            .commit()
    }

    override fun onStartDrag(viewHolder: BaseViewHolder) {
        itemTouchHelper?.startDrag(viewHolder)
    }

    override fun onDeleteFavorite(favoriteContentEntity: FavoriteContentEntity) {
        presenter.deleteFavorite(favoriteContentEntity)
    }

    override fun onSaveNewList(newList: List<FavoriteContentEntity>) {
        presenter.onSaveNewList(newList)
    }

    override fun onDetach() {
        binding = null
        super.onDetach()
    }

    companion object {
        fun newInstance(): Fragment = FavoritesFragment()
    }
}