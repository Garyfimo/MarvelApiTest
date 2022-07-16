package com.garyfimo.marvelapitest.data.model

import com.squareup.moshi.Json

data class Data(
    @field:Json(name = "offset") val offset : Int,
    @field:Json(name = "limit") val limit : Int,
    @field:Json(name = "total") val total : Int,
    @field:Json(name = "count") val count : Int,
    @field:Json(name = "results") val characters : List<Character>,
)