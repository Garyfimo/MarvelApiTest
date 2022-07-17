package com.garyfimo.marvelapitest.domain.character

import com.garyfimo.marvelapitest.domain.character.model.MarvelCharacter

interface MarvelRepository {
    suspend fun getCharacters(): RequestStatus<List<MarvelCharacter>, Exception>
    suspend fun getCharacterById(characterId: Int): RequestStatus<MarvelCharacter, Exception>
}