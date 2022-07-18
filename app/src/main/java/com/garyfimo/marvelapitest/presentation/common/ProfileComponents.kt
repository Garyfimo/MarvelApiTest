package com.garyfimo.marvelapitest.presentation.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.garyfimo.marvelapitest.R
import com.garyfimo.marvelapitest.domain.character.model.MarvelCharacter

@Composable
fun ProfileCard(
    marvelCharacter: MarvelCharacter,
    isClickable: Boolean = true,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .wrapContentHeight(align = Alignment.Top)
            .clickable(onClick = onClick, enabled = isClickable),
        elevation = 8.dp,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            ProfilePicture(
                imageUrl = marvelCharacter.thumbnailUrl,
                imageSize = 70.dp
            )
            ProfileContent(
                username = marvelCharacter.name,
                description = marvelCharacter.description,
                alignment = Alignment.Start,
                maxLines = 1,
                overFlow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun ProfilePicture(imageUrl: String, imageSize: Dp) {
    Card(
        shape = CircleShape,
        border = BorderStroke(
            width = 2.dp,
            color = if (imageUrl.contains("image_not_available")) Color.Red else Color.Green
        ),
        modifier = Modifier
            .padding(16.dp)
            .size(imageSize),
        elevation = 4.dp,
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = "Profile photo",
            modifier = Modifier.size(72.dp),
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
fun ProfileContent(
    username: String, description: String,
    alignment: Alignment.Horizontal,
    maxLines: Int,
    overFlow: TextOverflow
) {
    Column(
        modifier = Modifier
            .padding(8.dp),
        horizontalAlignment = alignment
    ) {
        CompositionLocalProvider(
            LocalContentAlpha provides (if (description.isNotEmpty()) ContentAlpha.high else ContentAlpha.medium)
        ) {
            Text(
                text = username,
                style = MaterialTheme.typography.h5
            )
        }
        Spacer(modifier = Modifier.size(8.dp))
        CompositionLocalProvider(LocalContentAlpha provides (ContentAlpha.medium)) {
            Text(
                text = description.takeIf { description.isNotEmpty() }
                    ?: stringResource(id = R.string.character_description, username),
                style = MaterialTheme.typography.body1,
                maxLines = maxLines,
                overflow = overFlow
            )
        }
    }
}