package com.example.rootstrapinterview.ui.characters.viewmodel

import androidx.lifecycle.*
import com.example.rootstrapinterview.data.api.model.character.Character
import com.example.rootstrapinterview.domain.Repo
import com.example.rootstrapinterview.utils.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CharactersViewModel(private val repo: Repo) : ViewModel() {

    var page = 1
    var characterList = MutableLiveData<Resource<ArrayList<Character>>>()

    fun getCharacters(nextPage: Boolean) {

        if (nextPage) {
            page += 1
        } else {
            page = 1
        }

        viewModelScope.launch {
            characterList.postValue(Resource.Loading())

            val result = repo.getCharacters(page)

            characterList.postValue(result)
        }
    }
}