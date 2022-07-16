package com.garyfimo.marvelapitest.data.util

import org.junit.Assert.*
import org.junit.Test

class UriHelperTest {

    @Test
    fun test_return_url_with_https_when_http_is_given() {
        val url = "http://fakeurl.com"
        val expectedUrl = "https://fakeurl.com"
        val resultUrl = UriHelper.convertHttpToHttps(url)
        assertEquals(expectedUrl, resultUrl)
    }

    @Test
    fun test_return_url_with_https_when_https_is_given() {
        val url = "https://fakeurl.com"
        val expectedUrl = "https://fakeurl.com"
        val resultUrl = UriHelper.convertHttpToHttps(url)
        assertEquals(expectedUrl, resultUrl)
    }
}