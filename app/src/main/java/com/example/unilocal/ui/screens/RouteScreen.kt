package com.example.unilocal.ui.screens

import kotlinx.serialization.Serializable

@Serializable
sealed class RouteScreen {
    @Serializable
    object Auth : RouteScreen()

    @Serializable
    object Home : RouteScreen()
}