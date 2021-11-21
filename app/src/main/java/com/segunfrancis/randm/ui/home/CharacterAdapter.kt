package com.segunfrancis.randm.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import com.segunfrancis.randm.R
import com.segunfrancis.randm.databinding.ItemCharacterBinding
import com.segunfrancis.randm.util.loadImage

class CharacterAdapter(private val imageLoader: ImageLoader) :
    ListAdapter<Character, CharacterAdapter.CharacterViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(
            ItemCharacterBinding.bind(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_character, parent, false
                )
            )
        )
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CharacterViewHolder(private val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(character: Character) = with(binding) {
            characterName.text = character.name
            characterImage.loadImage(character.image, imageLoader)
            root.setOnClickListener { character.onClick?.invoke(character) }
        }
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Character>() {
            override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
                return oldItem == newItem
            }
        }
    }
}
