package com.katyrin.nasa_md.ui.main.fragments.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.katyrin.nasa_md.R
import com.katyrin.nasa_md.databinding.DotItemBinding

class DotsRecyclerViewAdapter(private val countItems: Int, context: Context)
    : RecyclerView.Adapter<DotsRecyclerViewAdapter.DotsViewHolder>() {

    var dotPosition: Int = 0
    private val drawablePassive: Drawable? =
        ContextCompat.getDrawable(context, R.drawable.swipe_indicator_passive)
    private val drawableActive: Drawable? =
        ContextCompat.getDrawable(context, R.drawable.swipe_indicator_active)

    inner class DotsViewHolder(private val itemBinding: DotItemBinding)
        : RecyclerView.ViewHolder(itemBinding.root) {
            fun bindPassive() {
                itemBinding.dot.setImageDrawable(drawablePassive)
            }
            fun bindActive() {
                itemBinding.dot.setImageDrawable(drawableActive)
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DotsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = DotItemBinding.inflate(layoutInflater, parent, false)
        return DotsViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: DotsViewHolder, position: Int) {
        if (position == dotPosition) {
            holder.bindActive()
        } else {
            holder.bindPassive()
        }
    }

    override fun getItemCount(): Int = countItems
}