package com.garyfimo.marvelapitest.data.util

import com.garyfimo.marvelapitest.data.model.Character
import com.garyfimo.marvelapitest.domain.character.model.MarvelCharacter

fun Character.toEntity(): MarvelCharacter {
    return MarvelCharacter(
        id = id,
        name = name,
        description = description,
        thumbnailUrl = "${thumbnail.path}.${thumbnail.extension}"
    )
}