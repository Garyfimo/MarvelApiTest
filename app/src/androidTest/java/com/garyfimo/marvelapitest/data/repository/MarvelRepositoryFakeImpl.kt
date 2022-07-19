package com.garyfimo.marvelapitest.data.repository

import com.garyfimo.marvelapitest.domain.character.BadMarvelRequestException
import com.garyfimo.marvelapitest.domain.character.MarvelRepository
import com.garyfimo.marvelapitest.domain.character.RequestStatus
import com.garyfimo.marvelapitest.domain.character.model.MarvelCharacter
import kotlinx.coroutines.delay
import javax.inject.Inject

class MarvelRepositoryFakeImpl @Inject constructor() : MarvelRepository {

    private var shouldResponseError = false
    private var shouldAwait = false

    override suspend fun getCharacters(): RequestStatus<List<MarvelCharacter>, Exception> {
        return if (shouldResponseError) RequestStatus.build { throw BadMarvelRequestException() }
        else {
            if (shouldAwait) delay(1000)
            RequestStatus.build { charactersResponse }
        }
    }

    override suspend fun getCharacterById(characterId: Int): RequestStatus<MarvelCharacter, Exception> {
        return if (shouldResponseError) RequestStatus.build { throw BadMarvelRequestException() }
        else {
            if (shouldAwait) delay(1000)
            RequestStatus.build { charactersResponse.first() }
        }
    }

    fun setShouldResponseError(shouldResponseError: Boolean) {
        this.shouldResponseError = shouldResponseError
    }

    fun setShouldAwait(shouldAwait: Boolean) {
        this.shouldAwait = shouldAwait
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