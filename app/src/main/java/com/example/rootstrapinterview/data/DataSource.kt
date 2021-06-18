package com.example.rootstrapinterview.data

import com.example.rootstrapinterview.data.api.model.character.Character
import com.example.rootstrapinterview.utils.Resource

interface DataSource {
    suspend fun getCharacters(page: Int): Resource<ArrayList<Character>>
}