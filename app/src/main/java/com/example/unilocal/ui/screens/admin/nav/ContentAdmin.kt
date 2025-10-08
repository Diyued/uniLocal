package com.example.unilocal.ui.screens.admin.nav

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.unilocal.ui.config.RouteScreen
import com.example.unilocal.ui.screens.admin.tabs.PendingPlacesScreen
import com.example.unilocal.ui.screens.admin.tabs.ReviewedPlacesScreen
import com.example.unilocal.ui.viewmodel.PlacesViewModel
import com.example.unilocal.utils.SharedPrefsUtil

@Composable
fun ContentAdmin(
    padding: PaddingValues,
    navController: NavHostController
){

    val context = LocalContext.current
    val placesViewModel: PlacesViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = RouteTab.PendingPlaces
    ){
        composable<RouteTab.PendingPlaces> {
            PendingPlacesScreen(
                padding = padding,
                placesViewModel = placesViewModel,

            )
        }

        composable<RouteTab.ReviewedPlaces> {
            ReviewedPlacesScreen(
                padding = padding,
                placesViewModel = placesViewModel,
            )
        }

    }
}