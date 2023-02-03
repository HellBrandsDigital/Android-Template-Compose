package com.hellbrandsdigital.navigation

import androidx.navigation.NamedNavArgument

object NavigationDirections {
    val StartScreen = object : NavigationCommand {
        override val arguments = emptyList<NamedNavArgument>()
        override val destination = "startScreen"
    }

    val HomeScreen = object : NavigationCommand {
        override val arguments = emptyList<NamedNavArgument>()
        override val destination = "homeScreen"
    }
}

object NavItemDirections {
    const val INBOX = "Inbox"
    const val ARTICLES = "Articles"
    const val DM = "DirectMessages"
    const val GROUPS = "Groups"
}
