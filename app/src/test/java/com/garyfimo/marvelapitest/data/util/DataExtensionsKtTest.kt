package com.garyfimo.marvelapitest.data.util

import org.junit.Assert.assertEquals
import org.junit.Test

class DataExtensionsKtTest {

    @Test
    fun `test replace http by https`() {
        val value = "http://fake.com"
        val expected = "https://fake.com"

        assertEquals(expected, value.replaceHttpByHttps())
    }
    @Test
    fun `test return same value`() {
        val value = "https://fake.com"
        val expected = "https://fake.com"

        assertEquals(expected, value.replaceHttpByHttps())
    }
}