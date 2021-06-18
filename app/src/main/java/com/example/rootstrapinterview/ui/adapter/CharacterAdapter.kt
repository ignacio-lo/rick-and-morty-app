package com.example.rootstrapinterview.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rootstrapinterview.databinding.CharacterItemBinding
import com.example.rootstrapinterview.R
import com.example.rootstrapinterview.ui.adapter.viewholder.BaseViewHolder
import com.example.rootstrapinterview.data.api.model.character.Character
import kotlin.collections.ArrayList

class CharacterAdapter(private val context: Context, private var list: ArrayList<Character>, private val listener: CharacterActions) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface CharacterActions{
        fun onCharacterClicked(image: String, name: String, species: String, status: String)
    }

    fun addCharacters(data: ArrayList<Character>) {
        list.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return CharacterViewHolder(LayoutInflater.from(context).inflate(R.layout.character_item, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder) {
            is CharacterViewHolder -> holder.bind(list[position], position)
        }
    }

    inner class CharacterViewHolder(itemView: View) : BaseViewHolder<Character>(itemView) {
        private val binding = CharacterItemBinding.bind(itemView)

        override fun bind(item: Character, position: Int) {

            Glide.with(context)
                .load(item.image)
                .centerCrop()
                .into(binding.cvImageCharacter)

            itemView.setOnClickListener {
                listener.onCharacterClicked(item.image, item.name, item.species, item.status)
            }
        }
    }
}