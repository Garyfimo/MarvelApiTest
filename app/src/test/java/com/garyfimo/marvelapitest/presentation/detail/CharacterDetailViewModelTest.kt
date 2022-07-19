package com.garyfimo.marvelapitest.presentation.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.garyfimo.marvelapitest.domain.character.BadMarvelRequestException
import com.garyfimo.marvelapitest.domain.character.MarvelRepository
import com.garyfimo.marvelapitest.domain.character.NetworkException
import com.garyfimo.marvelapitest.domain.character.RequestStatus
import com.garyfimo.marvelapitest.domain.character.UnrecoverableException
import com.garyfimo.marvelapitest.domain.character.model.MarvelCharacter
import com.garyfimo.marvelapitest.presentation.ScreenStatus
import com.garyfimo.marvelapitest.utilTest.TestCoroutineRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterDetailViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var characterDetailViewModel: CharacterDetailViewModel
    private var mockMarvelRepository = mockk<MarvelRepository>(relaxed = true)

    @Before
    fun setUp() {
        initViewModelTest()
    }

    @Test
    fun `test screenStatus start`() {
        runTest {
            coEvery { mockMarvelRepository.getCharacterById(1010727) } returns RequestStatus.build { characterResponse }
            val expected = ScreenStatus.Start
            val job = launch { characterDetailViewModel.getCharacterById(1010727) }
            val result = characterDetailViewModel.characterDetailStatus.value
            Assert.assertEquals(expected, result)
            job.cancel()
        }
    }

    @Test
    fun `test screenStatus loading`() {
        runTest {
            coEvery { mockMarvelRepository.getCharacterById(1010727) } coAnswers {
                delay(1000)
                RequestStatus.build { characterResponse }
            }
            val expected = ScreenStatus.Loading
            val job = launch { characterDetailViewModel.getCharacterById(1010727) }
            // needs a delay to wait for response
            delay(1000)
            val result = characterDetailViewModel.characterDetailStatus.value
            Assert.assertEquals(expected, result)
            job.cancel()
        }
    }

    @Test
    fun `test screenStatus success when repository return success`() {
        runTest {
            coEvery { mockMarvelRepository.getCharacterById(1010727) } returns RequestStatus.build { characterResponse }
            val expected = ScreenStatus.Success(characterResponse)
            val job = launch { characterDetailViewModel.getCharacterById(1010727) }
            // needs a delay to wait for response
            delay(1)
            val result = characterDetailViewModel.characterDetailStatus.value
            Assert.assertEquals(expected, result)
            job.cancel()
        }
    }

    @Test
    fun `test screenStatus error when repository return BadMarvelRequestException`() {
        runTest {
            coEvery { mockMarvelRepository.getCharacterById(1010727) } returns RequestStatus.build {
                throw  BadMarvelRequestException()
            }
            val expected = ScreenStatus.Error
            val job = launch { characterDetailViewModel.getCharacterById(1010727) }
            // needs a delay to wait for response
            delay(1)
            val result = characterDetailViewModel.characterDetailStatus.value
            Assert.assertEquals(expected, result)
            job.cancel()
        }
    }

    @Test
    fun `test screenStatus error when repository return NetworkException`() {
        runTest {
            coEvery { mockMarvelRepository.getCharacterById(1010727) } returns RequestStatus.build {
                throw  NetworkException()
            }
            val expected = ScreenStatus.Error
            val job = launch { characterDetailViewModel.getCharacterById(1010727) }
            // needs a delay to wait for response
            delay(1)
            val result = characterDetailViewModel.characterDetailStatus.value
            Assert.assertEquals(expected, result)
            job.cancel()
        }
    }

    @Test
    fun `test screenStatus error when repository return UnrecoverableException`() {
        runTest {
            coEvery { mockMarvelRepository.getCharacterById(1010727) } returns RequestStatus.build {
                throw  UnrecoverableException()
            }
            val expected = ScreenStatus.Error
            val job = launch { characterDetailViewModel.getCharacterById(1010727) }
            // needs a delay to wait for response
            delay(1)
            val result = characterDetailViewModel.characterDetailStatus.value
            Assert.assertEquals(expected, result)
            job.cancel()
        }
    }

    private fun initViewModelTest() {
        characterDetailViewModel = CharacterDetailViewModel(
            marvelRepository = mockMarvelRepository
        )
    }

    private val characterResponse =
        MarvelCharacter(
            id = 1010727,
            name = "Spider-dok",
            description = "",
            thumbnailUrl = ""
        )
}