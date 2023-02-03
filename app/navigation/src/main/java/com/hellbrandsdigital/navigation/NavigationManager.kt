package com.hellbrandsdigital.navigation

import kotlinx.coroutines.flow.MutableStateFlow

class NavigationManager {
    var commands = MutableStateFlow(NavigationDirections.StartScreen)

    fun navigate(direction: NavigationCommand) {
        commands.value = direction
    }
}
