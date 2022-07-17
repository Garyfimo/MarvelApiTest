package com.garyfimo.marvelapitest.data.repository

import com.garyfimo.marvelapitest.data.api.MarvelService
import com.garyfimo.marvelapitest.data.util.HashGenerator
import com.garyfimo.marvelapitest.data.util.toEntity
import com.garyfimo.marvelapitest.domain.character.MarvelRepository
import com.garyfimo.marvelapitest.domain.character.RequestStatus
import com.garyfimo.marvelapitest.domain.character.model.MarvelCharacter

class MarvelRepositoryImpl(
    private val service: MarvelService,
    private val hashGenerator: HashGenerator,
    private val publicKey: String,
    private val privateKey: String,
) : MarvelRepository {

    override suspend fun getCharacters(): RequestStatus<List<MarvelCharacter>, Exception> {
        val timestamp = System.currentTimeMillis()
        val hash = "$timestamp$privateKey$publicKey"
        val response = service.getCharacters(
            timestamp = timestamp,
            md5Digest = hashGenerator.buildMD5Digest(hash),
            apiKey = publicKey
        )
        return if (response.code == 200) {
            RequestStatus.build { response.data.characters.map { it.toEntity() } }
        } else {
            RequestStatus.build { throw Exception(response.status) }
        }
    }

    override suspend fun getCharacterById(characterId: Int): RequestStatus<MarvelCharacter, Exception> {
        val timestamp = System.currentTimeMillis()
        val hash = "$timestamp$privateKey$publicKey"
        val response = service.getCharacters(
            timestamp = timestamp,
            md5Digest = hashGenerator.buildMD5Digest(hash),
            apiKey = publicKey
        )
        return if (response.code == 200) {
            RequestStatus.build { response.data.characters.first().toEntity() }
        } else {
            RequestStatus.build { throw Exception(response.status) }
        }
    }
}