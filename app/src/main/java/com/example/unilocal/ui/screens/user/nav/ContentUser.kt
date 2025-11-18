package com.example.unilocal.ui.screens.user.nav

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.unilocal.ui.screens.user.tabs.CreatePlaceScreen
import com.example.unilocal.ui.screens.user.tabs.EditProfileScreen
import com.example.unilocal.ui.screens.user.tabs.FavoritePlacesScreen
import com.example.unilocal.ui.screens.user.tabs.MapScreen
import com.example.unilocal.ui.screens.user.tabs.Places
import com.example.unilocal.ui.screens.user.tabs.PlaceDetail
import com.example.unilocal.ui.viewmodel.MainViewModel
import com.example.unilocal.utils.SharedPrefsUtil

@Composable
fun ContentUser(
    userId: String,
    padding: PaddingValues,
    navController: NavHostController,
    mainViewModel: MainViewModel,
    setShowFAB: (Boolean) -> Unit
) {
    val context = LocalContext.current
    val user = SharedPrefsUtil.getPreference(context)
    val placesViewModel = mainViewModel.placesViewModel
    val reviewsViewModel = mainViewModel.reviewsViewModel




    NavHost(
        navController = navController,
        startDestination = RouteTab.Map
    ){
        composable<RouteTab.Map> {
            MapScreen(
                padding = padding,
                onNavigateToPlaceDetail ={
                navController.navigate(RouteTab.PlaceDetail(it))
            }
            )
        }


        composable<RouteTab.Favorites> {
            FavoritePlacesScreen(
                padding = padding,
                onViewClick = { placeId ->
                    navController.navigate(RouteTab.PlaceDetail(placeId))
                },
                mainViewModel = mainViewModel
            )
        }

        composable<RouteTab.CreatePlace> {
            CreatePlaceScreen(
                userId = user["userId"],
                onNavigateBack = {
                    navController.navigate(RouteTab.Map)
                },
                placesViewModel = placesViewModel,
                onImageSelected = {
                    imageUrl ->
                }
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
            Places(
                userId = user["userId"]!!,
                padding = padding,
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
                placeId = args.id,
                mainViewModel = mainViewModel

            )
        }

    }

}
