package com.example.unilocal.ui.screens.user.tabs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.unilocal.ui.viewmodel.PlacesViewModel
import coil.compose.AsyncImage
import com.example.unilocal.ui.components.PlacesList
import com.example.unilocal.ui.nav.LocalMainViewModel

@Composable
fun Places(
    padding: PaddingValues,
    onNavigateToPlaceDetail: (String) -> Unit){

    val placesViewModel = LocalMainViewModel.current.placesViewModel
    val places by placesViewModel.places.collectAsState()


    PlacesList(
        places = places,
        padding = padding,
        onNavigateToPlaceDetail = onNavigateToPlaceDetail
    )
}