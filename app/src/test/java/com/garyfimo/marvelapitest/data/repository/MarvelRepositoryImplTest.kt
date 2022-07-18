package com.garyfimo.marvelapitest.data.repository

import com.garyfimo.marvelapitest.data.api.MarvelService
import com.garyfimo.marvelapitest.data.util.ApiKeyInterceptor
import com.garyfimo.marvelapitest.data.util.HashGenerator
import com.garyfimo.marvelapitest.domain.character.RequestStatus
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.nio.charset.StandardCharsets

class MarvelRepositoryImplTest {

    private val hashGenerator = mockk<HashGenerator>()
    private val publicKey = "public_key"
    private val privateKey = "private_key"
    private val mockWebServer = MockWebServer()

    private val marvelService = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .client(
            OkHttpClient
                .Builder()
                .addInterceptor(ApiKeyInterceptor(publicKey))
                .build()
        )
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(MarvelService::class.java)

    private val marvelRepository = MarvelRepositoryImpl(
        marvelService,
        hashGenerator,
        publicKey,
        privateKey
    )

    @After
    fun tearDown() {
        unmockkAll()
        mockWebServer.shutdown()
    }

    @Test
    fun `test request fails when request characters`() {
        every { hashGenerator.buildMD5Digest(any()) } returns ""
        mockWebServer.enqueueResponse(
            responseCode = 409,
            filePath = "error_response_marvel_api.json"
        )

        runBlocking {
            val charactersResponse = marvelRepository.getCharacters()
            val isError = charactersResponse is RequestStatus.Error
            assert(isError)
        }
    }

    @Test
    fun `test request returns 409 when request characters`() {
        every { hashGenerator.buildMD5Digest(any()) } returns ""
        mockWebServer.enqueueResponse(
            responseCode = 409,
            filePath = "error_response_marvel_api.json"
        )

        runBlocking {
            val charactersResponse = marvelRepository.getCharacterById(1010727)
            val isError = charactersResponse is RequestStatus.Error
            assert(isError)
        }
    }

    @Test
    fun `test request success and character info is valid`() {
        every { hashGenerator.buildMD5Digest(any()) } returns ""
        mockWebServer.enqueueResponse(filePath = "success_response_marvel_api.json")

        runBlocking {
            val charactersResponse = marvelRepository.getCharacters()
            val isSuccess = charactersResponse is RequestStatus.Success
            assert(isSuccess)
            if (charactersResponse is RequestStatus.Success) {
                val characters = charactersResponse.value
                assertEquals(2, characters.size)
                assertEquals("3-D Man", characters[0].name)
                assertEquals("A-Bomb (HAS)", characters[1].name)
            }
        }
    }

    @Test
    fun `test request fails when request character`() {
        every { hashGenerator.buildMD5Digest(any()) } returns ""
        mockWebServer.enqueueResponse(filePath = "error_response_marvel_api.json")

        runBlocking {
            val charactersResponse = marvelRepository.getCharacterById(1010727)
            val isError = charactersResponse is RequestStatus.Error
            assert(isError)
        }
    }


    @Test
    fun `test request returns 409 when request character`() {
        every { hashGenerator.buildMD5Digest(any()) } returns ""
        mockWebServer.enqueueResponse(
            responseCode = 409,
            filePath = "error_response_marvel_api.json"
        )

        runBlocking {
            val charactersResponse = marvelRepository.getCharacterById(1010727)
            val isError = charactersResponse is RequestStatus.Error
            assert(isError)
        }
    }

    @Test
    fun `test request success and character info is valid when id is given`() {
        every { hashGenerator.buildMD5Digest(any()) } returns ""
        mockWebServer.enqueueResponse(filePath = "success_response_spider-dok_marvel_api.json")

        runBlocking {
            val charactersResponse = marvelRepository.getCharacterById(1010727)
            val isSuccess = charactersResponse is RequestStatus.Success
            assert(isSuccess)
            if (charactersResponse is RequestStatus.Success) {
                val character = charactersResponse.value
                assertEquals("Spider-dok", character.name)
                assertEquals("", character.description)
            }
        }
    }
}

fun MockWebServer.enqueueResponse(responseCode: Int = 200, filePath: String) {
    val inputStream = javaClass.classLoader?.getResourceAsStream(filePath)
    val source = inputStream?.source()?.buffer()
    source?.let {
        enqueue(
            MockResponse()
                .setResponseCode(responseCode)
                .setBody(it.readString(StandardCharsets.UTF_8))
        )
    }
}