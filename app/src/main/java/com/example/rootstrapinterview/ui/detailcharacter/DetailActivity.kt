package com.example.rootstrapinterview.ui.detailcharacter

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.rootstrapinterview.R
import com.example.rootstrapinterview.data.api.model.character.Character
import com.example.rootstrapinterview.databinding.ActivityDetailBinding
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        val image = intent.getStringExtra(characterImg)!!
        val name = intent.getStringExtra(name)!!
        val species = intent.getStringExtra(species)!!
        val status = intent.getStringExtra(status)!!

        val charac = Character(0, name, status, species, image)

        setDetailView(charac)
    }

    private fun setDetailView(charac: Character) {
        Glide.with(this)
            .load(charac.image)
            .centerCrop()
            .into(binding.cvImageCharacter)

        binding.tvNameCharacter.text = charac.name

        binding.tvStatusCharacter.text = charac.status

        binding.tvSpeciesCharacter.text = charac.species
    }

    companion object {

        private const val characterImg = "image"
        private const val name = "name"
        private const val species = "species"
        private const val status = "status"

        fun createIntent(context: Context, imgCharac: String, nameCharac: String, speciesCharac: String, statusCharac: String): Intent {
            return Intent(context, DetailActivity::class.java).apply {
                putExtra(characterImg, imgCharac)
                putExtra(name, nameCharac)
                putExtra(species, speciesCharac)
                putExtra(status, statusCharac)
            }
        }
    }
}