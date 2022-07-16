package com.garyfimo.marvelapitest.data.model

import com.squareup.moshi.Json

data class Character(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "description") val description: String,
    @field:Json(name = "thumbnail") val thumbnail: Image,
)