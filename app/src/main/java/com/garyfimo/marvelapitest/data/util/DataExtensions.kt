package com.garyfimo.marvelapitest.data.util

fun String.replaceHttpByHttps(): String {
    return if (this.contains("https")) {
        this
    } else {
        this.replace("http", "https")
    }
}