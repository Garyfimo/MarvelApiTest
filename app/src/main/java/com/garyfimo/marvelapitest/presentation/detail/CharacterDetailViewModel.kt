package com.garyfimo.marvelapitest.presentation.detail

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
class CharacterDetailViewModel @Inject constructor(
    private val marvelRepository: MarvelRepository
) : ViewModel() {

    private val _characterDetailStatus =
        MutableStateFlow<ScreenStatus<MarvelCharacter>>(ScreenStatus.Start)

    val characterDetailStatus: StateFlow<ScreenStatus<MarvelCharacter>> =
        _characterDetailStatus

    fun getCharacterById(characterId: Int) {
        viewModelScope.launch {
            _characterDetailStatus.value = ScreenStatus.Loading
            val response = marvelRepository.getCharacterById(characterId)
            if (response is RequestStatus.Success) {
                _characterDetailStatus.value = ScreenStatus.Success(response.value)
            } else {
                response as RequestStatus.Error
                _characterDetailStatus.value = ScreenStatus.Error(response.error.message)
            }
        }
    }
}