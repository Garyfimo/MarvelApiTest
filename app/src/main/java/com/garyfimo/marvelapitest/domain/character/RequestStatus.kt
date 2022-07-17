package com.garyfimo.marvelapitest.domain.character

sealed class RequestStatus<out S, out E> {

    data class Success<out S>(val value: S) : RequestStatus<S, Nothing>()
    data class Error<out E>(val error: E) : RequestStatus<Nothing, E>()
    companion object {
        inline fun <S> build(function: () -> S): RequestStatus<S, Exception> =
            try {
                Success(function.invoke())
            } catch (ex: Exception) {
                Error(ex)
            }
    }
}