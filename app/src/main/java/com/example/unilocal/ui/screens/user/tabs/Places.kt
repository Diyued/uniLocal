package com.example.unilocal.ui.screens.user.tabs

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.unilocal.ui.components.PlacesList
import com.example.unilocal.ui.nav.LocalMainViewModel
import com.example.unilocal.ui.viewmodel.MainViewModel
import com.example.unilocal.ui.viewmodel.PlacesViewModel

@Composable
fun Places(
    userId: String,
    padding: PaddingValues,
    onNavigateToPlaceDetail: (String) -> Unit){
    val placesViewModel = LocalMainViewModel.current.placesViewModel
    placesViewModel.loadMyPlaces(userId)
    val myPlaces by placesViewModel.myApprovedPlaces.collectAsState()


    PlacesList(
        places = myPlaces,
        padding = padding,
        onNavigateToPlaceDetail = onNavigateToPlaceDetail
    )
}