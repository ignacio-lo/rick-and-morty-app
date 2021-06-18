package com.example.rootstrapinterview.domain

import com.example.rootstrapinterview.data.api.model.character.Character
import com.example.rootstrapinterview.utils.Resource

interface Repo {
    suspend fun getCharacters(page: Int): Resource<ArrayList<Character>>
}