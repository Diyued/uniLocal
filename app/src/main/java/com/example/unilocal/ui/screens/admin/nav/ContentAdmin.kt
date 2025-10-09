package com.example.unilocal.ui.screens.admin.nav

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.unilocal.ui.config.RouteScreen
import com.example.unilocal.ui.screens.admin.tabs.PendingPlacesScreen
import com.example.unilocal.ui.screens.admin.tabs.PlaceValidationScreen
import com.example.unilocal.ui.screens.admin.tabs.ReviewedPlacesScreen
import com.example.unilocal.ui.viewmodel.MainViewModel
import com.example.unilocal.ui.viewmodel.PlacesViewModel
import com.example.unilocal.utils.SharedPrefsUtil

@Composable
fun ContentAdmin(
    mainViewModel: MainViewModel,
    padding: PaddingValues,
    navController: NavHostController
){

    val context = LocalContext.current


    NavHost(
        navController = navController,
        startDestination = RouteTab.PendingPlaces
    ){
        composable<RouteTab.PendingPlaces> {
            PendingPlacesScreen(
                padding = padding,
                placesViewModel = mainViewModel.placesViewModel,
                navController = navController
            )
        }

        composable<RouteTab.ReviewedPlaces> {
            ReviewedPlacesScreen(
                padding = padding,
                placesViewModel = mainViewModel.placesViewModel,
            )
        }

        composable<RouteTab.PlaceValidation> { backStackEntry ->
            val args: RouteTab.PlaceValidation = backStackEntry.toRoute()
            val placeId = args.placeId

            PlaceValidationScreen(
                padding = padding,
                placesViewModel = mainViewModel.placesViewModel,
                placeId = placeId,
                onNavigateBack = { navController.popBackStack() } // Para volver atrás
            )


        }


    }
}