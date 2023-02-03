package com.hellbrandsdigital.androidtemplatecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoTracker
import com.hellbrandsdigital.androidtemplatecompose.ui.theme.AndroidTemplateComposeTheme
import com.hellbrandsdigital.homescreen.HomeScreenApp
import com.hellbrandsdigital.homescreen.HomeScreenViewModel
import com.hellbrandsdigital.homescreen.ReplyHomeUIState
import com.hellbrandsdigital.homescreen.data.local.LocalEmailsDataProvider
import com.hellbrandsdigital.navigation.NavigationDirections
import com.hellbrandsdigital.navigation.NavigationManager
import com.hellbrandsdigital.startscreen.StartScreenUI
import com.hellbrandsdigital.utils.DevicePosture
import com.hellbrandsdigital.utils.isBookPosture
import com.hellbrandsdigital.utils.isSeparating
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationManager: NavigationManager

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * Flow of [DevicePosture] that emits every time there's a change in the windowLayoutInfo
         */
        val devicePostureFlow = WindowInfoTracker.getOrCreate(this).windowLayoutInfo(this)
            .flowWithLifecycle(this.lifecycle)
            .map { layoutInfo ->
                val foldingFeature =
                    layoutInfo.displayFeatures
                        .filterIsInstance<FoldingFeature>()
                        .firstOrNull()
                when {
                    isBookPosture(foldingFeature) ->
                        DevicePosture.BookPosture(foldingFeature.bounds)

                    isSeparating(foldingFeature) ->
                        DevicePosture.Separating(foldingFeature.bounds, foldingFeature.orientation)

                    else -> DevicePosture.NormalPosture
                }
            }
            .stateIn(
                scope = lifecycleScope,
                started = SharingStarted.Eagerly,
                initialValue = DevicePosture.NormalPosture
            )
        setContent {
            AndroidTemplateComposeTheme {
                val devicePosture = devicePostureFlow.collectAsState().value
                val windowSize = calculateWindowSizeClass(activity = this)
                MainUI(devicePosture, windowSize)
            }
        }
    }

    @Composable
    private fun MainUI(devicePosture: DevicePosture, windowSize: WindowSizeClass) {
        SetUpNavigation(windowSize.widthSizeClass, devicePosture)
    }

    @Composable
    private fun SetUpNavigation(
        windowSize: WindowWidthSizeClass,
        devicePosture: DevicePosture
    ) {
        val navController = rememberNavController()

        @Composable
        fun startScreen() = StartScreenUI().StartScreen(
            viewModel = hiltViewModel(),
            windowSize = windowSize,
            devicePosture = devicePosture
        )

        @Composable
        fun homeScreen() = HomeScreenApp(
            windowSize = windowSize,
            foldingDevicePosture = devicePosture,
            homeUIState = (hiltViewModel() as HomeScreenViewModel).uiState.collectAsState().value
        )

        NavHost(
            navController = navController,
            startDestination = NavigationDirections.StartScreen.destination
        )
        {
            composable(NavigationDirections.StartScreen.destination) {
                startScreen()
            }

            composable(NavigationDirections.HomeScreen.destination) {
                homeScreen()
            }
        }
        navigationManager.commands.collectAsState().value.also {
            navController.navigate(it.destination)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReplyAppPreview() {
    AndroidTemplateComposeTheme {
        HomeScreenApp(
            homeUIState = ReplyHomeUIState(emails = LocalEmailsDataProvider.allEmails),
            windowSize = WindowWidthSizeClass.Compact,
            foldingDevicePosture = DevicePosture.NormalPosture
        )
    }
}

@Preview(showBackground = true, widthDp = 700)
@Composable
fun ReplyAppPreviewTablet() {
    AndroidTemplateComposeTheme {
        HomeScreenApp(
            homeUIState = ReplyHomeUIState(emails = LocalEmailsDataProvider.allEmails),
            windowSize = WindowWidthSizeClass.Medium,
            foldingDevicePosture = DevicePosture.NormalPosture
        )
    }
}

@Preview(showBackground = true, widthDp = 1000)
@Composable
fun ReplyAppPreviewDesktop() {
    AndroidTemplateComposeTheme {
        HomeScreenApp(
            homeUIState = ReplyHomeUIState(emails = LocalEmailsDataProvider.allEmails),
            windowSize = WindowWidthSizeClass.Expanded,
            foldingDevicePosture = DevicePosture.NormalPosture
        )
    }
}
