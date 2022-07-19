package com.garyfimo.marvelapitest.presentation.util

import org.junit.Assert.assertEquals
import org.junit.Test

class PresentationExtensionKtTest{

    @Test
    fun test_return_first_word_when_two_words_string_is_given(){
        val value = "two words"
        val expected = "two"

        assertEquals(expected, value.takeFirstWord())
    }

    @Test
    fun test_return_first_word_when_one_word_string_is_given(){
        val value = "one"
        val expected = "one"

        assertEquals(expected, value.takeFirstWord())
    }

    @Test
    fun test_return_empty_when_empty_string_is_given(){
        val value = ""
        val expected = ""

        assertEquals(expected, value.takeFirstWord())
    }
}