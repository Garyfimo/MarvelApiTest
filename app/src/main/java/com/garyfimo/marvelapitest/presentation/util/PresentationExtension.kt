package com.garyfimo.marvelapitest.presentation.util

fun String.takeFirstWord() : String{
    return this.split(" ").first()
}