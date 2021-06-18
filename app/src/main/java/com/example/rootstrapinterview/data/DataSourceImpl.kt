package com.example.rootstrapinterview.data

import com.example.rootstrapinterview.utils.Resource
import com.example.rootstrapinterview.utils.RetrofitClient
import com.example.rootstrapinterview.data.api.model.character.Character

class DataSourceImpl() : DataSource {

    override suspend fun getCharacters(page: Int): Resource<ArrayList<Character>> {
        return Resource.Success(RetrofitClient.apiService.getCharacters(page)?.results ?: arrayListOf())
    }
}