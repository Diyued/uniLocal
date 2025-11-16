package com.example.unilocal.ui.screens.user.tabs


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import com.example.unilocal.ui.components.PlacesList
import com.example.unilocal.ui.nav.LocalMainViewModel
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Map(
    padding: PaddingValues,
    onNavigateToPlaceDetail: (String) -> Unit
){
    val placesViewModel = LocalMainViewModel.current.placesViewModel
    var query by rememberSaveable { mutableStateOf("") }
    var expanded by rememberSaveable { mutableStateOf(false) }

    MapboxMap(
        Modifier.fillMaxSize(),
        mapViewportState = rememberMapViewportState {
            setCameraOptions {
                zoom(9.0)
                center(Point.fromLngLat(-75.6491181, 4.4687891))
                pitch(45.0)
            }
        },
    )
    Column (
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        SearchBar(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp),
            inputField = {

                SearchBarDefaults.InputField(
                    query = query,
                    onQueryChange = {
                        query = it
                    },
                    onSearch = {
                        expanded = false
                    },
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                    placeholder = { Text("Search") }
                )
            },
            expanded = expanded,
            onExpandedChange = { expanded = it },
        ){
            if (query.isNotEmpty()) {
                val places = placesViewModel.findyName(query)

                LazyColumn {
                    items(places){
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable{
                                    query = it.title
                                    expanded = false
                                }
                                .padding(8.dp),
                            text = it.title
                        )
                        HorizontalDivider()
                    }
                }
            }

        }
        if (query.isNotEmpty()){
            val places = placesViewModel.findyName(query)
            PlacesList(
                places = places,
                padding = PaddingValues(72.dp),
                onNavigateToPlaceDetail = onNavigateToPlaceDetail
            )
        }
    }
}
