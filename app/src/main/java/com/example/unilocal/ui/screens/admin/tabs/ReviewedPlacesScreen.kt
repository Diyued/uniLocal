package com.example.unilocal.ui.screens.admin.tabs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.example.unilocal.ui.screens.admin.nav.RouteTab
import com.example.unilocal.ui.viewmodel.PlacesViewModel

@Composable
fun ReviewedPlacesScreen(
    padding: PaddingValues,
    placesViewModel: PlacesViewModel
){

    val places by placesViewModel.reviewedPlaces.collectAsState()

    LazyColumn(modifier = Modifier.padding(paddingValues = padding)) {
        items(places){
            ListItem(
                modifier = Modifier.clip(MaterialTheme.shapes.small).clickable{
                    //onNavigateToPlaceDetail(it.id)
                },
                headlineContent = {
                    Text(text = it.title)
                },
                supportingContent = {
                    Text(text = it.description)
                },
                trailingContent = {
                    Text(text = it.status.toString())
                }

            )


        }
    }

}