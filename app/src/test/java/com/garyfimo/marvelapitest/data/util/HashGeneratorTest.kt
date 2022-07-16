package com.garyfimo.marvelapitest.data.util

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class HashGeneratorTest {

    private lateinit var hashGenerator: HashGenerator

    @Before
    fun setUp() {
        hashGenerator = HashGenerator()
    }

    @Test
    fun test_return_md5_hash_when_message_is_given() {
        val message = "this_is_a_message"
        val expectedValue = "da231b1a7b815cb83f9dda27cca1ba8a"
        val result = hashGenerator.buildMD5Digest(message)
        assertEquals(expectedValue, result)
    }
}