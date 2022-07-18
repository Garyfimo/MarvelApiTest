package com.garyfimo.marvelapitest.presentation.list

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.garyfimo.marvelapitest.domain.character.MarvelRepository
import com.garyfimo.marvelapitest.domain.character.RequestStatus
import com.garyfimo.marvelapitest.domain.character.model.MarvelCharacter
import com.garyfimo.marvelapitest.presentation.ScreenStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val marvelRepository: MarvelRepository
) : ViewModel() {

    private val _characterListStatus =
        MutableStateFlow<ScreenStatus<List<MarvelCharacter>>>(ScreenStatus.Start)
    val characterListStatus: StateFlow<ScreenStatus<List<MarvelCharacter>>> = _characterListStatus

    init {
        getCharacters()
    }

    @VisibleForTesting
    fun getCharacters() {
        viewModelScope.launch {
            _characterListStatus.value = ScreenStatus.Loading
            val response = marvelRepository.getCharacters()
            if (response is RequestStatus.Success) {
                _characterListStatus.value = ScreenStatus.Success(response.value)
            } else {
                response as RequestStatus.Error
                _characterListStatus.value = ScreenStatus.Error(response.error.message)
            }
        }
    }
}