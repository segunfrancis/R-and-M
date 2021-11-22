package com.segunfrancis.randm.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.segunfrancis.randm.R
import com.segunfrancis.randm.databinding.ItemEpisodeBinding

class EpisodeAdapter(private val onEpisodeClick: (String?) -> Unit) :
    ListAdapter<Episode, EpisodeAdapter.DetailViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        return DetailViewHolder(
            ItemEpisodeBinding.bind(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_episode, parent, false
                )
            )
        )
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class DetailViewHolder(private val binding: ItemEpisodeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(episode: Episode) = with(binding) {
            episodeName.text = episode.name
            episodeNumber.text = episode.id
            root.setOnClickListener { onEpisodeClick.invoke(episode.id) }
        }
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Episode>() {
            override fun areItemsTheSame(oldItem: Episode, newItem: Episode): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Episode, newItem: Episode): Boolean {
                return oldItem == newItem
            }
        }
    }
}
