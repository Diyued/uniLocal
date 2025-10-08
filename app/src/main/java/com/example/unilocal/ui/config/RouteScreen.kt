package com.example.unilocal.ui.config

import kotlinx.serialization.Serializable

@Serializable
sealed class RouteScreen {

    @Serializable
    data object Home : RouteScreen()

    @Serializable
    data object Login : RouteScreen()

    @Serializable
    data object Register : RouteScreen()



}