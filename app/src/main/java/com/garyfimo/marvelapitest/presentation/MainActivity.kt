package com.garyfimo.marvelapitest.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.garyfimo.marvelapitest.presentation.common.ChangeSystemUiController
import com.garyfimo.marvelapitest.presentation.ui.theme.MarvelAPITestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarvelAPITestTheme {
                ChangeSystemUiController()
                MarvelApiTestNavigation()
            }
        }
    }
}