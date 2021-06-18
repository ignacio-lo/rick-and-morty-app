package com.example.rootstrapinterview.data.api.model.character

import com.google.gson.annotations.SerializedName

data class CharacterResponse (
    @SerializedName("results")
    val results: ArrayList<Character>
)