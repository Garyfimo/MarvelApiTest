@file:Suppress("UNCHECKED_CAST")

package com.garyfimo.marvelapitest.presentation.common

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.garyfimo.marvelapitest.R
import com.garyfimo.marvelapitest.presentation.ScreenStatus
import com.garyfimo.marvelapitest.presentation.triggerDeeplinkLinkedInGaryfimo
import com.garyfimo.marvelapitest.presentation.ui.theme.MarvelLightRed
import com.garyfimo.marvelapitest.presentation.ui.theme.MarvelRed
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun <T> ScreenStatus(
    state: ScreenStatus<Any>,
    errorText: String,
    onError: () -> Unit,
    onSuccess: @Composable (value: T) -> Unit
) {
    when (state) {
        is ScreenStatus.Start -> {
            LoadingScreen()
        }
        is ScreenStatus.Loading -> {
            LoadingScreen()
        }
        is ScreenStatus.Success -> {
            onSuccess(state.value as T)
        }
        is ScreenStatus.Error -> {
            ErrorScreen(errorText, onError)
        }
    }
}

const val AppBarIcon = "app_bar_icon"

@Composable
fun AppBar(title: String, icon: ImageVector, onClick: () -> Unit) {
    TopAppBar(
        navigationIcon = {
            Icon(
                imageVector = icon,
                contentDescription = stringResource(id = R.string.top_app_bar),
                modifier = Modifier
                    .testTag(AppBarIcon)
                    .padding(horizontal = 12.dp)
                    .clickable(onClick = onClick),
            )
        },
        title = { Text(text = title) }
    )
}

@Composable
fun DrawerView() {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
            .padding(start = 16.dp, top = 64.dp),
    ) {
        val infiniteTransition = rememberInfiniteTransition()
        val angle by infiniteTransition.animateFloat(
            initialValue = 360F,
            targetValue = 0F,
            animationSpec = infiniteRepeatable(
                animation = tween(5000, easing = LinearEasing)
            )
        )
        Image(
            painter = painterResource(id = R.drawable.ic_batman_profile),
            contentDescription = stringResource(id = R.string.batman_profile_text),
            colorFilter = ColorFilter.tint(Color.White),
            modifier = Modifier
                .size(76.dp)
                .rotate(angle)
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = stringResource(id = R.string.me),
            color = Color.White,
            style = MaterialTheme.typography.h5,
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            text = stringResource(id = R.string.contact_me),
            color = Color.White,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.clickable { context.triggerDeeplinkLinkedInGaryfimo() }
        )
    }
}

@Composable
fun ErrorScreen(
    errorText: String,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MarvelRed
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_shield_logo),
                contentDescription = stringResource(id = R.string.shield_logo_text),
                modifier = Modifier
                    .size(148.dp)
            )
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = stringResource(id = R.string.marvel_error_text),
                color = Color.White,
                style = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = errorText,
                color = Color.White,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.clickable(onClick = onClick)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MarvelLightRed
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val infiniteTransition = rememberInfiniteTransition()
            val angle by infiniteTransition.animateFloat(
                initialValue = 0F,
                targetValue = 360F,
                animationSpec = infiniteRepeatable(
                    animation = tween(2000, easing = LinearEasing)
                )
            )
            Image(
                painter = painterResource(id = R.drawable.ic_shield_logo),
                contentDescription = stringResource(id = R.string.shield_logo_text),
                modifier = Modifier
                    .size(148.dp)
                    .rotate(angle)
            )
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = stringResource(id = R.string.marvel_loading_text),
                color = Color.White,
                style = MaterialTheme.typography.body2
            )
        }
    }
}

@Composable
fun ChangeSystemUiController() {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = MaterialTheme.colors.isLight
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = useDarkIcons
        )
    }
}