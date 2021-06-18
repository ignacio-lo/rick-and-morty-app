package com.example.rootstrapinterview.domain

import com.example.rootstrapinterview.data.api.model.character.Character
import com.example.rootstrapinterview.utils.Resource
import com.example.rootstrapinterview.data.DataSource

class RepoImpl(private val dataSource: DataSource) : Repo {

    override suspend fun getCharacters(page: Int): Resource<ArrayList<Character>> {
        return dataSource.getCharacters(page)
    }
}