package com.katyrin.nasa_md.ui.main.fragments.adapters.notes

interface ItemTouchHelperAdapter {
    fun onItemMove(fromPosition: Int, toPosition: Int)

    fun onItemDismiss(position: Int)
}