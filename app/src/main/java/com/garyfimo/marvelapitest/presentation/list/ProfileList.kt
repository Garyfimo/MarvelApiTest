package com.garyfimo.marvelapitest.presentation.list

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.garyfimo.marvelapitest.R
import com.garyfimo.marvelapitest.domain.character.model.MarvelCharacter
import com.garyfimo.marvelapitest.presentation.common.AppBar
import com.garyfimo.marvelapitest.presentation.common.DrawerView
import com.garyfimo.marvelapitest.presentation.common.ProfileCard
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
        onError = {}
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
                ProfileList(profileList) { profile ->
                    navController.navigate("character_detail/${profile.id}")
                }
            }
        }
    }
}

const val LIST_TEST_TAG = "list_tag"

@Composable
fun ProfileList(
    characterProfiles: List<MarvelCharacter>,
    onCharacterCardClicked: (MarvelCharacter) -> Unit
) {
    LazyColumn(modifier = Modifier.testTag(LIST_TEST_TAG)) {
        items(
            characterProfiles
        ) { characterProfile ->
            ProfileCard(
                marvelCharacter = characterProfile,
                onClick = { onCharacterCardClicked.invoke(characterProfile) })
        }
    }
}