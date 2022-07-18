package com.garyfimo.marvelapitest.presentation

sealed class ScreenStatus<out T : Any> {
    object Start : ScreenStatus<Nothing>()
    object Loading : ScreenStatus<Nothing>()
    data class Success<out T : Any>(val value: T) : ScreenStatus<T>()
    data class Error(val message: String?) : ScreenStatus<Nothing>()
}