package com.example.unilocal.ui.screens.user.nav

import com.example.unilocal.ui.config.RouteScreen
import kotlinx.serialization.Serializable

@Serializable
sealed class RouteTab {

    @Serializable
    data object Map : RouteTab()

    @Serializable
    data object Favorites : RouteTab()

    @Serializable
    data object CreatePlace : RouteTab()

    @Serializable
    data object Search : RouteTab()

    //TEMPORAL
    @Serializable
    data object Places : RouteTab()

    @Serializable
    data class PlaceDetail(val id: String): RouteTab()
    @Serializable
    data object EditProfileScreen : RouteTab()


}