package com.garyfimo.marvelapitest.data.model

import com.squareup.moshi.Json

data class Image(
    @field:Json(name = "path") val path : String,
    @field:Json(name = "extension") val extension : String,
)