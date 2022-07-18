package com.garyfimo.marvelapitest.presentation.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.garyfimo.marvelapitest.R
import com.garyfimo.marvelapitest.domain.character.model.MarvelCharacter
import com.garyfimo.marvelapitest.presentation.common.AppBar
import com.garyfimo.marvelapitest.presentation.common.ProfileContent
import com.garyfimo.marvelapitest.presentation.common.ProfilePicture
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
            Surface(modifier = Modifier.fillMaxSize()) {
                ProfileDetail(character)
            }
        }
    }

}

@Composable
fun ProfileDetail(
    marvelCharacter: MarvelCharacter
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        ProfilePicture(
            imageUrl = marvelCharacter.thumbnailUrl,
            imageSize = 240.dp
        )
        ProfileContent(
            username = marvelCharacter.name,
            description = marvelCharacter.description,
            alignment = Alignment.CenterHorizontally,
            maxLines = Int.MAX_VALUE,
            overFlow = TextOverflow.Visible
        )
    }
}