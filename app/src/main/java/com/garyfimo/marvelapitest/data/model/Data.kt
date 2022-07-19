package com.garyfimo.marvelapitest.data.model

import com.squareup.moshi.Json

data class Data(
    @field:Json(name = "results") val characters : List<Character>,
)