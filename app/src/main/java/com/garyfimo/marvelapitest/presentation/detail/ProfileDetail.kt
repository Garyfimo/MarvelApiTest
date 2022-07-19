package com.garyfimo.marvelapitest.presentation.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.garyfimo.marvelapitest.R
import com.garyfimo.marvelapitest.domain.character.model.MarvelCharacter
import com.garyfimo.marvelapitest.presentation.common.AppBar
import com.garyfimo.marvelapitest.presentation.common.ProfileImage
import com.garyfimo.marvelapitest.presentation.common.ScreenStatus

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProfileDetailScreen(
    characterDetailViewModel: CharacterDetailViewModel = hiltViewModel(),
    characterId: Int,
    navController: NavHostController
) {
    val needsRequest = remember { mutableStateOf(true) }
    if (needsRequest.value) {
        characterDetailViewModel.getCharacterById(characterId)
        needsRequest.value = false
    }
    ScreenStatus<MarvelCharacter>(
        state = characterDetailViewModel.characterDetailStatus.collectAsState().value,
        errorText = stringResource(id = R.string.marvel_error_click_to_go_back_text),
        onError = { navController.navigateUp() }
    ) { character ->
        Scaffold(
            topBar = {
                AppBar(
                    title = stringResource(id = R.string.marvel_character_detail, character.name),
                    icon = Icons.Default.ArrowBack
                ) {
                    navController.navigateUp()
                }
            },
        ) { _ ->
            ProfileDetail(character)
        }
    }

}

@Composable
fun ProfileDetail(
    marvelCharacter: MarvelCharacter
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        ProfileImage(
            marvelCharacter = marvelCharacter,
            size = 344.dp
        )
        Column(
            modifier = Modifier
                .padding(8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            CompositionLocalProvider(
                LocalContentAlpha provides (ContentAlpha.high)
            ) {
                Text(
                    text = marvelCharacter.name,
                    style = MaterialTheme.typography.h5,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = marvelCharacter.description.takeIf { marvelCharacter.description.isNotEmpty() }
                        ?: stringResource(
                            id = R.string.character_description,
                            marvelCharacter.name
                        ),
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}