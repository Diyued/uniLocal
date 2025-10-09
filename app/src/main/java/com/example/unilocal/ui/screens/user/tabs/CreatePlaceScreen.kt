package com.example.unilocal.ui.screens.user.tabs

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.unilocal.model.City
import com.example.unilocal.model.Location
import com.example.unilocal.model.Place
import com.example.unilocal.model.PlaceType
import com.example.unilocal.model.Schedule
import com.example.unilocal.ui.components.CustomButton
import com.example.unilocal.ui.components.CustomTextField
import com.example.unilocal.ui.viewmodel.PlacesViewModel
import java.time.DayOfWeek
import java.time.LocalTime
import java.util.UUID

@Composable
fun CreatePlaceScreen(
    userId: String?,
    placesViewModel: PlacesViewModel,
    onNavigateBack: () -> Unit
){

    var id by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    val schedule = remember { mutableStateListOf(
        Schedule(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(18, 0))
    ) }
    var city by remember { mutableStateOf("") }
    var phones by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var pictures by remember { mutableStateOf("") }
    var images by remember { mutableStateOf("") }
    var showExitDialog by remember { mutableStateOf(false) }


    BackHandler(
        !showExitDialog
    ) {
        showExitDialog = true
    }
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Add a new place", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(20.dp))

            CustomTextField(label = "Place name", value = title, onValueChange = { title = it })
            Spacer(modifier = Modifier.height(12.dp))

            CustomTextField(label = "Description", value = description, onValueChange = { description = it })
            Spacer(modifier = Modifier.height(12.dp))

            CustomTextField(label = "Address", value = address, onValueChange = { address = it })
            Spacer(modifier = Modifier.height(12.dp))

            CustomTextField(label = "City", value = city, onValueChange = { city = it })
            Spacer(modifier = Modifier.height(12.dp))

            CustomTextField(label = "Category", value = type, onValueChange = { type = it })
            Spacer(modifier = Modifier.height(12.dp))

            CustomTextField(label = "Pictures", value = pictures, onValueChange = { pictures = it })
            Spacer(modifier = Modifier.height(12.dp))

            CustomButton(text = "Request", onClick = {
                val place = Place(
                    id = UUID.randomUUID().toString(),
                    title = title,
                    description = description,
                    city = city as City,
                    address = address,
                    location = Location(1.0,1.0),
                    images = listOf(),
                    phoneNumber = phones,
                    type = type as PlaceType,
                    schedules = schedule,
                    ownerId = userId ?: ""

                )
                placesViewModel.create(place)
            })

        }
    }
    if(showExitDialog){
        AlertDialog(
            title = {
                "Esta seguro de salir?"
            },
            text = {
                Text("Al salir perderas todos los cambios")
                },
            onDismissRequest = {
                showExitDialog = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showExitDialog = false
                        onNavigateBack()

                }){
                    Text("Confirmar")
                }
            },
            dismissButton = {
            }
        )
    }
}
