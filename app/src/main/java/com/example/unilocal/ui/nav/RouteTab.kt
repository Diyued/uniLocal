package com.example.unilocal.ui.nav

import kotlinx.serialization.Serializable

@Serializable
sealed class RouteTab {

    @Serializable
    data object Map : RouteTab()

    @Serializable
    data object Favorites : RouteTab()

    @Serializable
    data object CreatePlace : RouteTab()

}