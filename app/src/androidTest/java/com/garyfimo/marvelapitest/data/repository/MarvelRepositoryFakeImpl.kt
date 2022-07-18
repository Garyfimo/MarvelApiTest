package com.garyfimo.marvelapitest.data.repository

import com.garyfimo.marvelapitest.domain.character.BadMarvelRequestException
import com.garyfimo.marvelapitest.domain.character.MarvelRepository
import com.garyfimo.marvelapitest.domain.character.RequestStatus
import com.garyfimo.marvelapitest.domain.character.model.MarvelCharacter
import javax.inject.Inject

class MarvelRepositoryFakeImpl @Inject constructor() : MarvelRepository {

    private var shouldResponseError = false

    override suspend fun getCharacters(): RequestStatus<List<MarvelCharacter>, Exception> {
        return if (shouldResponseError) RequestStatus.build { throw BadMarvelRequestException("this is an error") }
        else RequestStatus.build { charactersResponse }
    }

    override suspend fun getCharacterById(characterId: Int): RequestStatus<MarvelCharacter, Exception> {
        return if (shouldResponseError) RequestStatus.build { throw BadMarvelRequestException("this is an error") }
        else RequestStatus.build { charactersResponse.first() }
    }

    fun setShouldResponseError(shouldResponseError: Boolean) {
        this.shouldResponseError = shouldResponseError
    }

    private val charactersResponse = arrayListOf(
        MarvelCharacter(
            id = 1010727,
            name = "Spider-dok",
            description = "",
            thumbnailUrl = ""
        ),
        MarvelCharacter(
            id = 1017100,
            name = "A-Bomb (HAS)",
            description = "",
            thumbnailUrl = ""
        )
    )
}