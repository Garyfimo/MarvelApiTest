package com.garyfimo.marvelapitest.domain.character

interface MarvelRepository {
    suspend fun getCharacters(): RequestStatus<List<MarvelCharacter>>
    suspend fun getCharacterById(characterId : Int): RequestStatus<MarvelCharacter>
}