package com.garyfimo.marvelapitest.data.util

import java.math.BigInteger
import java.security.MessageDigest
import javax.inject.Inject
import javax.inject.Singleton

private const val ALGORITHM_MD5 = "MD5"

@Singleton
class HashGenerator @Inject constructor() {

    fun buildMD5Digest(message: String): String {
        return run {
            val messageDigest = MessageDigest.getInstance(ALGORITHM_MD5)
            val bytes = messageDigest.digest(message.toByteArray())
            BigInteger(1, bytes).toString(16)
        }
    }
}