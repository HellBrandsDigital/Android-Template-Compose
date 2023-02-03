package com.hellbrandsdigital.startscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import com.hellbrandsdigital.navigation.NavigationDirections
import com.hellbrandsdigital.navigation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StartScreenViewModel @Inject constructor(): ViewModel() {

    @Inject
    lateinit var navigationManager: NavigationManager

    fun navigateToHomeScreen() {
        Log.d("StartScreenViewModel", "navigateToHomeScreen")
        navigationManager.navigate(NavigationDirections.HomeScreen)
    }
}
