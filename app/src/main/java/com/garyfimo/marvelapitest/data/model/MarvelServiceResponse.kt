package com.garyfimo.marvelapitest.data.model

import com.squareup.moshi.Json

data class MarvelServiceResponse(
    @field:Json(name = "code") val code: Int,
    @field:Json(name = "status") val status: String,
    @field:Json(name = "data") val data: Data,
)
