package com.example.unilocal.ui.screens.admin.tabs

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.error
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.unilocal.ui.viewmodel.PlacesViewModel
import androidx.compose.foundation.clickable
import com.example.unilocal.ui.config.RouteScreen
import com.example.unilocal.ui.screens.admin.nav.RouteTab
@Composable
fun PendingPlacesScreen(
    padding: PaddingValues,
    placesViewModel: PlacesViewModel,
    navController: NavController
) {

    val pendingPlaces by placesViewModel.pendingPlaces.collectAsState()

    if (pendingPlaces.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Text("No hay lugares pendientes por revisar.")
        }
    } else {
        LazyColumn(
            modifier = Modifier.padding(padding),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(pendingPlaces, key = { it.id }) { place ->
                Card(
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.clickable {
                        navController.navigate(RouteTab.PlaceValidation(place.id))
                    }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(place.title, style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(place.description, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}
