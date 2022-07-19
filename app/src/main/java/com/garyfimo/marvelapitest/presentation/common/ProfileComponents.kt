package com.garyfimo.marvelapitest.presentation.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import coil.compose.AsyncImage
import com.garyfimo.marvelapitest.domain.character.model.MarvelCharacter

@Composable
fun ProfileImage(
    marvelCharacter: MarvelCharacter,
    @DrawableRes errorDrawable: Int,
    size: Dp
) {
    AsyncImage(
        model = marvelCharacter.thumbnailUrl,
        contentDescription = "${marvelCharacter.name} photo",
        contentScale = ContentScale.FillWidth,
        modifier = Modifier
            .height(size),
        error = painterResource(id = errorDrawable)
    )
}