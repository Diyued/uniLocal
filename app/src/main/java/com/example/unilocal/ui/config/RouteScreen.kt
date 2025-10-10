package com.example.unilocal.ui.config

import kotlinx.serialization.Serializable

@Serializable
sealed class RouteScreen {

    @Serializable
    data object HomeUser : RouteScreen()

    @Serializable
    data object HomeAdmin : RouteScreen()

    @Serializable
    data object Login : RouteScreen()

    @Serializable
    data object Register : RouteScreen()

    @Serializable
    data object PasswordReset : RouteScreen()

    @Serializable
    data class PasswordResetCode(val userId: String) : RouteScreen()

}