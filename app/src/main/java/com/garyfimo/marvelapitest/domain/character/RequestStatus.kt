package com.garyfimo.marvelapitest.domain.character

sealed class RequestStatus<out S, out E> {

    data class Success<out S>(val valor: S) : RequestStatus<Nothing, S>()
    data class Error<out E>(val error: E) : RequestStatus<E, Nothing>()
    companion object {
        inline fun <S> build(function: () -> S): RequestStatus<Exception, S> =
            try {
                Success(function.invoke())
            } catch (ex: Exception) {
                Error(ex)
            }
    }
}