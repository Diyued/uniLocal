package com.example.unilocal.ui.screens.admin.nav

import com.example.unilocal.ui.config.RouteScreen
import com.example.unilocal.ui.screens.admin.nav.RouteTab
import kotlinx.serialization.Serializable

@Serializable
sealed class RouteTab {

    @Serializable
    data object PendingPlaces : RouteTab()

    @Serializable
    data object ReviewedPlaces : RouteTab()

    @Serializable
    data class PlaceValidation(val placeId: String) : RouteTab()

}