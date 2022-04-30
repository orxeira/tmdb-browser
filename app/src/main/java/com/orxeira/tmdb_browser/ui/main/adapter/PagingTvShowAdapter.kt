package com.orxeira.tmdb_browser.ui.main.adapter

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.orxeira.tmdb_browser.R
import com.orxeira.tmdb_browser.common.inflate
import com.orxeira.tmdb_browser.databinding.ItemTvShowBinding
import com.orxeira.tmdb_browser.domain.TvShow

class PagingTvShowAdapter(private val listener: (TvShow) -> Unit) :
    PagingDataAdapter<TvShow, PagingTvShowAdapter.ViewHolder>(TvShowComparator) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { tvShow ->
            holder.bind(tvShow)
            holder.itemView.setOnClickListener { listener(tvShow) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.item_tv_show, false)
        return ViewHolder(view)
    }

    object TvShowComparator : DiffUtil.ItemCallback<TvShow>() {
        override fun areItemsTheSame(oldItem: TvShow, newItem: TvShow) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: TvShow, newItem: TvShow) =
            oldItem == newItem
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemTvShowBinding.bind(view)
        fun bind(tvShow: TvShow) {
            binding.tvShow = tvShow
        }
    }
}