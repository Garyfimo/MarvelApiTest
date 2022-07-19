package com.garyfimo.marvelapitest.presentation.list

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.garyfimo.marvelapitest.R
import com.garyfimo.marvelapitest.domain.character.model.MarvelCharacter
import com.garyfimo.marvelapitest.presentation.common.AppBar
import com.garyfimo.marvelapitest.presentation.common.DrawerView
import com.garyfimo.marvelapitest.presentation.common.ProfileImage
import com.garyfimo.marvelapitest.presentation.common.ScreenStatus
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProfileListScreen(
    viewModel: CharacterListViewModel = hiltViewModel(),
    navController: NavHostController
) {
    ScreenStatus<List<MarvelCharacter>>(
        state = viewModel.characterListStatus.collectAsState().value,
        errorText = stringResource(id = R.string.marvel_error_click_to_retry_text),
        onError = { viewModel.getCharacters() }
    ) { profileList ->
        val scaffoldState = rememberScaffoldState()
        val scope = rememberCoroutineScope()
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                AppBar(
                    title = stringResource(id = R.string.marvel_character_list),
                    icon = Icons.Default.Home,
                ) {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                }
            },
            drawerContent = { DrawerView() }
        ) { _ ->
            Surface(modifier = Modifier.fillMaxSize()) {
                ProfileGridList(profileList) { profile ->
                    navController.navigate("character_detail/${profile.id}")
                }
            }
        }
    }
}

const val LIST_TEST_TAG = "list_tag"

@Composable
fun ProfileGridList(
    characterProfiles: List<MarvelCharacter>,
    onCharacterCardClicked: (MarvelCharacter) -> Unit
) {
    LazyVerticalGrid(
        modifier = Modifier.testTag(LIST_TEST_TAG),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(
            start = 8.dp,
            top = 12.dp,
            end = 8.dp,
            bottom = 12.dp
        )
    ) {
        items(
            characterProfiles.size
        ) { index ->
            ProfileListCard(
                marvelCharacter = characterProfiles[index],
                onClick = { onCharacterCardClicked.invoke(characterProfiles[index]) })
        }
    }
}

@Composable
fun ProfileListCard(
    marvelCharacter: MarvelCharacter,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .wrapContentHeight(align = Alignment.Top)
            .clickable(onClick = onClick),
        elevation = 8.dp,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            ProfileImage(
                marvelCharacter = marvelCharacter,
                size = 72.dp
            )
            Text(
                text = marvelCharacter.name.split(" ").first(),
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            )
            Spacer(modifier = Modifier.size(8.dp))
            CompositionLocalProvider(LocalContentAlpha provides (ContentAlpha.medium)) {
                Text(
                    modifier = Modifier.padding(start = 4.dp, end = 4.dp),
                    text = marvelCharacter.description.takeIf { marvelCharacter.description.isNotEmpty() }
                        ?: stringResource(
                            id = R.string.character_description,
                            marvelCharacter.name
                        ),
                    style = MaterialTheme.typography.body2,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}