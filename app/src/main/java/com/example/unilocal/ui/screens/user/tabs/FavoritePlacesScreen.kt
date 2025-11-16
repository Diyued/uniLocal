package com.example.unilocal.ui.screens.user.tabs

import android.R.attr.padding
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.isEmpty
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unilocal.model.Place
import com.example.unilocal.ui.components.PlacesList
import com.example.unilocal.ui.viewmodel.MainViewModel
import com.example.unilocal.ui.viewmodel.PlacesViewModel
import com.example.unilocal.ui.viewmodel.UsersViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritePlacesScreen(
    padding: PaddingValues,
    mainViewModel: MainViewModel,
    onViewClick: (String) -> Unit
) {


    val currentUser by mainViewModel.usersViewModel.currentUser.collectAsState()
    val allPlaces by mainViewModel.placesViewModel.places.collectAsState()
    val user = mainViewModel.usersViewModel.getUserById(currentUser?.id ?: "")


    val favoritePlaces = remember(currentUser, allPlaces) {
        currentUser?.favoritePlaces?.mapNotNull { placeId ->
            allPlaces.find { it.id == placeId}
        } ?: emptyList()
    }



    var selectedPlaceId by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Favorite Places") }
            )
        }
    ) { paddingValues ->

        if (currentUser == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("User not found")
            }

        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {

                Text(
                    text = "Saved Places",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(12.dp))

                // 📜 Lista de lugares favoritos

            if (favoritePlaces.isEmpty()) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No favorite places yet",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        items = favoritePlaces,
                        key = { place -> place.id }  // 🔑 Key para optimizar recomposición
                    ) { place ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedPlaceId = place.id },
                            shape = MaterialTheme.shapes.medium,
                            colors = CardDefaults.cardColors(
                                containerColor =
                                    if (selectedPlaceId == place.id)
                                        MaterialTheme.colorScheme.primaryContainer
                                    else
                                        MaterialTheme.colorScheme.surface
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(12.dp)
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Place,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column {
                                        Text(
                                            text = place.title,
                                            style = MaterialTheme.typography.bodyLarge
                                        )
                                        Text(
                                            text = place.address,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }



                                IconButton(
                                    onClick = {
                                        mainViewModel.usersViewModel.removeFavoritePlace(currentUser!!.id, place.id)
                                        if (selectedPlaceId == place.id) {
                                            selectedPlaceId = null
                                        }
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Delete favorite",
                                        tint = Color.Red
                                    )
                                }
                            }
                        }
                    }


                }
            }

                Button(
                    onClick = { selectedPlaceId?.let { onViewClick(it) } },
                    enabled = selectedPlaceId != null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("View Place")
                }

                // 🎯 Botón para ver lugar

            }

//


        }


    }
}