package com.garyfimo.marvelapitest.data.model

import com.squareup.moshi.Json

data class MarvelServiceResponse(
    @field:Json(name = "data") val data: Data,
)
