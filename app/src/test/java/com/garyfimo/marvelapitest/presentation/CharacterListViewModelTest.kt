package com.garyfimo.marvelapitest.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.garyfimo.marvelapitest.domain.character.BadMarvelRequestException
import com.garyfimo.marvelapitest.domain.character.MarvelRepository
import com.garyfimo.marvelapitest.domain.character.RequestStatus
import com.garyfimo.marvelapitest.domain.character.model.MarvelCharacter
import com.garyfimo.marvelapitest.presentation.list.CharacterListViewModel
import com.garyfimo.marvelapitest.utilTest.TestCoroutineRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.lang.Exception

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterListViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var characterListViewModel: CharacterListViewModel
    private var mockMarvelRepository = mockk<MarvelRepository>(relaxed = true)

    @Before
    fun setUp() {
        initViewModelTest()
    }

    @Test
    fun `test screenStatus start`() {
        runTest {
            coEvery { mockMarvelRepository.getCharacters() } returns RequestStatus.build { charactersResponse }
            val expected = ScreenStatus.Start
            val job = launch { characterListViewModel.getCharacters() }
            val result = characterListViewModel.characterListStatus.value
            assertEquals(expected, result)
            job.cancel()
        }
    }

    @Test
    fun `test screenStatus loading`() {
        runTest {
            coEvery { mockMarvelRepository.getCharacters() } coAnswers {
                delay(1000)
                RequestStatus.build { charactersResponse }
            }
            val expected = ScreenStatus.Loading
            val job = launch { characterListViewModel.getCharacters() }
            // needs a delay to wait for response
            delay(1000)
            val result = characterListViewModel.characterListStatus.value
            assertEquals(expected, result)
            job.cancel()
        }
    }

    @Test
    fun `test screenStatus success when repository return success`() {
        runTest {
            coEvery { mockMarvelRepository.getCharacters() } returns RequestStatus.build { charactersResponse }
            val expected = ScreenStatus.Success(charactersResponse)
            val job = launch { characterListViewModel.getCharacters() }
            // needs a delay to wait for response
            delay(1)
            val result = characterListViewModel.characterListStatus.value
            assertEquals(expected, result)
            job.cancel()
        }
    }

    @Test
    fun `test screenStatus error when repository return error`() {
        runTest {
            coEvery { mockMarvelRepository.getCharacters() } returns RequestStatus.build { throw  BadMarvelRequestException("this is error") }
            val expected = ScreenStatus.Error("this is error")
            val job = launch { characterListViewModel.getCharacters() }
            // needs a delay to wait for response
            delay(1)
            val result = characterListViewModel.characterListStatus.value
            assertEquals(expected, result)
            job.cancel()
        }
    }

    @Test
    fun `test screenStatus error when repository return error with null message`() {
        runTest {
            coEvery { mockMarvelRepository.getCharacters() } returns RequestStatus.build { throw  Exception() }
            val expected = ScreenStatus.Error("")
            val job = launch { characterListViewModel.getCharacters() }
            // needs a delay to wait for response
            delay(1)
            val result = characterListViewModel.characterListStatus.value
            assertEquals(expected, result)
            job.cancel()
        }
    }

    private fun initViewModelTest() {
        characterListViewModel = CharacterListViewModel(
            marvelRepository = mockMarvelRepository
        )
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