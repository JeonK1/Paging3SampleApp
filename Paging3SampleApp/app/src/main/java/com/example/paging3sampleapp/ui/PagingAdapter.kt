package com.example.paging3sampleapp.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.paging3sampleapp.databinding.ItemViewBinding
import com.example.paging3sampleapp.ui.model.ImageData

class PagingAdapter : PagingDataAdapter<ImageData, PagingAdapter.PagingViewHolder>(
    object : DiffUtil.ItemCallback<ImageData>() {
        override fun areItemsTheSame(oldItem: ImageData, newItem: ImageData): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ImageData, newItem: ImageData): Boolean =
            oldItem == newItem
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagingViewHolder {
        return PagingViewHolder(ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PagingViewHolder, position: Int) {
        holder.binding.apply {
            getItem(position)?.let {item ->
                tvTitle.text = "${position + 1}번째 아이템"
                tvSubTitle.text = "${item.author} (${item.width} x ${item.height})"
                tvContents.text = "${item.download_url}"
                Glide.with(holder.itemView)
                    .load(item.download_url)
                    .into(ivImage)
            }
        }
    }
    inner class PagingViewHolder(val binding: ItemViewBinding) : RecyclerView.ViewHolder(binding.root)
}