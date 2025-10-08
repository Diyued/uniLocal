package com.example.unilocal.ui.screens.user.nav

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.unilocal.ui.screens.user.tabs.CreatePlaceScreen
import com.example.unilocal.ui.screens.user.tabs.EditProfileScreen
import com.example.unilocal.ui.screens.user.tabs.Map
import com.example.unilocal.ui.screens.user.tabs.Places
import com.example.unilocal.ui.screens.user.tabs.Search
import com.example.unilocal.ui.screens.user.tabs.PlaceDetail
import com.example.unilocal.ui.viewmodel.PlacesViewModel

@Composable
fun ContentUser(
    padding: PaddingValues,
    navController: NavHostController
) {

    val placesViewModel: PlacesViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = RouteTab.Map
    ){
        composable<RouteTab.Map> {
            Map(
                padding = padding,
                onNavigateToPlaceDetail ={
                navController.navigate(RouteTab.PlaceDetail(it))
            }
            )
        }
        composable<RouteTab.Search> {
            Search(
                padding = padding
            )
        }

        composable<RouteTab.Favorites> {
            //FavoritesScreen()
        }

        composable<RouteTab.CreatePlace> {
            CreatePlaceScreen(
                onNavigateBack = {
                    navController.navigate(RouteTab.Map)
                },
                placesViewModel = placesViewModel
            )
        }
        composable<RouteTab.EditProfileScreen> {
            EditProfileScreen(
                onConfirmClick = {
                    navController.navigate(RouteTab.Map)
                }
            )
        }

        //TEMPORAL
        composable<RouteTab.TempPlaces> {


            Places(padding = padding,
                onNavigateToPlaceDetail ={
                    navController.navigate(RouteTab.PlaceDetail(it))
                }
            )
        }
        composable<RouteTab.PlaceDetail> {
            val args = it.toRoute<RouteTab.PlaceDetail>()
            PlaceDetail(
                id = args.id
            )
        }
    }

}
