package com.garyfimo.marvelapitest.data.repository

import com.garyfimo.marvelapitest.data.api.MarvelService
import com.garyfimo.marvelapitest.data.util.HashGenerator
import com.garyfimo.marvelapitest.data.util.toEntity
import com.garyfimo.marvelapitest.domain.character.BadMarvelRequestException
import com.garyfimo.marvelapitest.domain.character.MarvelRepository
import com.garyfimo.marvelapitest.domain.character.NetworkException
import com.garyfimo.marvelapitest.domain.character.RequestStatus
import com.garyfimo.marvelapitest.domain.character.UnrecoverableException
import com.garyfimo.marvelapitest.domain.character.model.MarvelCharacter
import okio.IOException
import retrofit2.HttpException

class MarvelRepositoryImpl(
    private val service: MarvelService,
    private val hashGenerator: HashGenerator,
    private val publicKey: String,
    private val privateKey: String,
) : MarvelRepository {

    override suspend fun getCharacters(): RequestStatus<List<MarvelCharacter>, Exception> {
        val timestamp = System.currentTimeMillis()
        val hash = "$timestamp$privateKey$publicKey"
        return runAndCatchErrors {
            val response = service.getCharacters(
                timestamp = timestamp,
                md5Digest = hashGenerator.buildMD5Digest(hash),
                apiKey = publicKey
            )
            RequestStatus.build { response.data.characters.map { it.toEntity() } }
        }
    }

    override suspend fun getCharacterById(characterId: Int): RequestStatus<MarvelCharacter, Exception> {
        val timestamp = System.currentTimeMillis()
        val hash = "$timestamp$privateKey$publicKey"
        return runAndCatchErrors {
            val response = service.getCharacterById(
                timestamp = timestamp,
                md5Digest = hashGenerator.buildMD5Digest(hash),
                apiKey = publicKey,
                id = characterId
            )
            RequestStatus.build { response.data.characters.first().toEntity() }
        }
    }

    private suspend fun <A> runAndCatchErrors(func: suspend () -> RequestStatus<A, Exception>): RequestStatus<A, Exception> =
        try {
            func.invoke()
        } catch (ex: HttpException) {
            RequestStatus.build { throw BadMarvelRequestException() }
        } catch (ex: IOException) {
            RequestStatus.build { throw NetworkException() }
        } catch (ex: Exception) {
            RequestStatus.build { throw UnrecoverableException() }
        }
}