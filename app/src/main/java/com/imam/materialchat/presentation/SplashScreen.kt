package com.imam.materialchat.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.imam.materialchat.R
import com.imam.materialchat.presentation.chatscreen.ChatScreen
import kotlinx.coroutines.delay

class SplashScreen : Screen {
    @Composable
    override fun Content() {
        SplashScreenContent()
    }
}

@Preview
@Composable
fun SplashScreenContent() {
    val navigator = LocalNavigator.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Gemini Chat",
                fontSize = 26.sp,
                fontFamily = FontFamily(Font(R.font.popinr)),
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        Text(
            text = "by Imam",
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(R.font.popinr)),
            color = MaterialTheme.colorScheme.onSurface
        )
    }

    LaunchedEffect(key1 = Unit) {
        delay(2000)
        navigator?.push(ChatScreen())
    }
}