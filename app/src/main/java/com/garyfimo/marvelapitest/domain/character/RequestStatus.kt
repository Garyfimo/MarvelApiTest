package com.garyfimo.marvelapitest.domain.character

sealed class RequestStatus<out T : Any> {
    data class Success<out T : Any>(val value: T) : RequestStatus<T>()
    data class Error(val errorMessage: String) : RequestStatus<Nothing>()
}