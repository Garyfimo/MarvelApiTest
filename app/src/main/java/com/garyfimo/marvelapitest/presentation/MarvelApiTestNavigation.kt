package com.garyfimo.marvelapitest.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.garyfimo.marvelapitest.presentation.detail.ProfileDetailScreen
import com.garyfimo.marvelapitest.presentation.list.ProfileListScreen

sealed class NavPath(val path: String) {
    object CharacterList : NavPath("character_list")
    object CharacterDetail : NavPath("character_detail/{characterId}")
}

@Composable
fun MarvelApiTestNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavPath.CharacterList.path) {
        composable(
            route = NavPath.CharacterList.path,
        ) {
            ProfileListScreen(navController = navController)
        }
        composable(
            route = NavPath.CharacterDetail.path,
            arguments = listOf(navArgument("characterId") {
                type = NavType.IntType
            })
        ) { navBackStackEntry ->
            ProfileDetailScreen(
                characterId = navBackStackEntry.arguments!!.getInt("characterId"),
                navController = navController
            )
        }
    }
}