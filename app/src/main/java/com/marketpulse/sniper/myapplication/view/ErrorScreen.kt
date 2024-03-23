package com.marketpulse.sniper.myapplication.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ErrorScreen(error: String) {
    Column(Modifier.fillMaxSize(), Arrangement.Center) {
        Text(modifier = Modifier.fillMaxWidth(), text = error, color = Color.Red, textAlign = TextAlign.Center)
    }
}

@Preview
@Composable
fun ErrorScreenPreview() {
    ErrorScreen(error = "Error")
}