package com.example.unilocal.ui.screens.user.nav

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.unilocal.model.User
import com.example.unilocal.ui.screens.user.tabs.CreatePlaceScreen
import com.example.unilocal.ui.screens.user.tabs.EditProfileScreen
import com.example.unilocal.ui.screens.user.tabs.FavoritePlacesScreen
import com.example.unilocal.ui.screens.user.tabs.Map
import com.example.unilocal.ui.screens.user.tabs.Places
import com.example.unilocal.ui.screens.user.tabs.PlaceDetail
import com.example.unilocal.ui.viewmodel.PlacesViewModel
import com.example.unilocal.utils.SharedPrefsUtil

@Composable
fun ContentUser(
    padding: PaddingValues,
    navController: NavHostController,
    placesViewModel: PlacesViewModel,
    setShowFAB: (Boolean) -> Unit
) {
    val context = LocalContext.current
    val user = SharedPrefsUtil.getPreference(context)



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


        composable<RouteTab.Favorites> {
            FavoritePlacesScreen(
                userId = user["userId"],
                onViewClick = { placeId ->
                    navController.navigate(RouteTab.PlaceDetail(placeId))
                },
                usersViewModel = viewModel(),
                placesViewModel = placesViewModel
            )
        }

        composable<RouteTab.CreatePlace> {
            CreatePlaceScreen(
                userId = user["userId"],
                onNavigateBack = {
                    navController.navigate(RouteTab.Map)
                },
                placesViewModel = placesViewModel
            )
        }
        composable<RouteTab.EditProfileScreen> {
            EditProfileScreen(
                userId = user["userId"],
                onConfirmClick = {
                    navController.navigate(RouteTab.Map)
                }
            )
        }

        //TEMPORAL
        composable<RouteTab.Places> {

            Log.d(placesViewModel.approvedPlaces.value.toString(), "PlacesViewModel")
            Places(padding = padding,
                placesViewModel = placesViewModel,
                onNavigateToPlaceDetail ={
                    navController.navigate(RouteTab.PlaceDetail(it))
                }
            )
        }
        composable<RouteTab.PlaceDetail> {
            setShowFAB(false)
            val args = it.toRoute<RouteTab.PlaceDetail>()
            PlaceDetail(
                userId = user["userId"],
                placeId = args.id
            )
        }

    }

}
