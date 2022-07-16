package com.garyfimo.marvelapitest.data.util

import android.net.Uri

object UriHelper {

    private const val HTTPS = "https"

    fun convertHttpToHttps(url: String): String {
        val uri = Uri.parse(url)
        if (uri.scheme == HTTPS) return url
        return "${HTTPS}://${uri.host}${uri.path}"
    }
}