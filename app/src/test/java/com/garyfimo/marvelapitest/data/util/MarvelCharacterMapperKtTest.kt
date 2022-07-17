package com.garyfimo.marvelapitest.data.util

import com.garyfimo.marvelapitest.data.model.Character
import com.garyfimo.marvelapitest.data.model.Image
import com.garyfimo.marvelapitest.domain.character.model.MarvelCharacter
import org.junit.Assert.assertEquals
import org.junit.Test


class MarvelCharacterMapperKtTest {

    @Test
    fun `test return marvel character when character from api is given`() {
        val character = Character(
            id = 1011334,
            name = "3-D Man",
            description = "",
            thumbnail = Image(
                path = "http://fakeurl.com/thumbnail",
                extension = "jpg"
            )
        )
        val expected = MarvelCharacter(
            id = 1011334,
            name = "3-D Man",
            description = "",
            thumbnailUrl = "http://fakeurl.com/thumbnail.jpg"
        )
        val response = character.toEntity()
        assertEquals(expected, response)
    }
}