package com.example.rootstrapinterview.data.api.model.character

import com.google.gson.annotations.SerializedName

data class Character(
    @SerializedName("id")
    val characterId: Int = 0,

    @SerializedName("name")
    val name: String = "",

    @SerializedName("status")
    val status: String = "",

    @SerializedName("species")
    val species: String = "",

    @SerializedName("image")
    val image: String = ""
)