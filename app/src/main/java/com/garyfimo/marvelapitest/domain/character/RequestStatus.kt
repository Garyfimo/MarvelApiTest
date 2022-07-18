package com.garyfimo.marvelapitest.domain.character

sealed class RequestStatus<out S, out E> {

    data class Success<out S>(val value: S) : RequestStatus<S, Nothing>()
    data class Error<out E>(val error: E) : RequestStatus<Nothing, E>()
    companion object {
        inline fun <S> build(function: () -> S): RequestStatus<S, BadMarvelRequestException> =
            try {
                Success(function.invoke())
            } catch (ex: Exception) {
                Error(BadMarvelRequestException(ex.message.orEmpty()))
            }
    }
}

class BadMarvelRequestException(message: String?) : Exception(message)