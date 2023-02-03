package com.hellbrandsdigital.startscreen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hellbrandsdigital.utils.DevicePosture

class StartScreenUI {

    @Composable
    fun StartScreen(
        viewModel: StartScreenViewModel,
        modifier: Modifier = Modifier,
        windowSize: WindowWidthSizeClass,
        devicePosture: DevicePosture
    ) {
        Surface(modifier = modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                IntroText(text = stringResource(id = R.string.start_screen_intro_text))
                Spacer(modifier = Modifier.height(16.dp))
                StartButton(onClick = { viewModel.navigateToHomeScreen() }, text = stringResource(id = R.string.start_screen_start_button_text))
            }
        }
    }

    @Composable
    private fun StartButton(onClick: () -> Unit, text: String, modifier: Modifier = Modifier) {
        Button(onClick = onClick, modifier = modifier) {
            Text(text = text)
        }
    }

    @Composable
    private fun IntroText(text: String, modifier: Modifier = Modifier) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineLarge,
            modifier = modifier
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun StartScreenPreviewHandy() {
        StartScreen(
            viewModel = StartScreenViewModel(),
            windowSize = WindowWidthSizeClass.Compact,
            devicePosture = DevicePosture.NormalPosture
        )
    }

    @Preview(showBackground = true, widthDp = 700)
    @Composable
    fun StartScreenPreviewTablet() {
        StartScreen(
            viewModel = StartScreenViewModel(),
            windowSize = WindowWidthSizeClass.Medium,
            devicePosture = DevicePosture.NormalPosture
        )
    }

    @Preview(showBackground = true, widthDp = 1000)
    @Composable
    fun StartScreenPreviewDesktop() {
        StartScreen(
            viewModel = StartScreenViewModel(),
            windowSize = WindowWidthSizeClass.Expanded,
            devicePosture = DevicePosture.NormalPosture
        )
    }

}
