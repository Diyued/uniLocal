package com.example.unilocal.ui.screens.user.tabs

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
) {

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var pictures by remember { mutableStateOf("") }
    var phones by remember { mutableStateOf("") }
    var showExitDialog by remember { mutableStateOf(false) }

    val schedule = remember {
        mutableStateListOf(
            Schedule(
                DayOfWeek.MONDAY,
                LocalTime.of(9, 0),
                LocalTime.of(18, 0)
            )
        )
    }

    val context = LocalContext.current

    // Back button handler
    BackHandler(!showExitDialog) {
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

            // Dropdowns
            CityDropdown(selectedCity = city, onCitySelected = { city = it })
            Spacer(modifier = Modifier.height(12.dp))

            PlaceTypeDropdown(selectedType = type, onTypeSelected = { type = it })
            Spacer(modifier = Modifier.height(12.dp))

            CustomTextField(label = "Pictures", value = pictures, onValueChange = { pictures = it })
            Spacer(modifier = Modifier.height(12.dp))

            CustomButton(text = "Request", onClick = {
                try {
                    val parsedCity = City.valueOf(city.uppercase())
                    val parsedType = PlaceType.valueOf(type.uppercase())

                    val place = Place(
                        id = UUID.randomUUID().toString(),
                        title = title,
                        description = description,
                        city = parsedCity,
                        address = address,
                        location = Location(1.0, 1.0),
                        images = listOf(),
                        phoneNumber = phones,
                        type = parsedType,
                        schedules = schedule,
                        ownerId = userId ?: ""
                    )

                    placesViewModel.create(place)
                    Toast.makeText(context, "Place creation requested successfully", Toast.LENGTH_SHORT).show()
                    onNavigateBack()

                } catch (e: IllegalArgumentException) {
                    Toast.makeText(context, "City or Category invalid", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    // Exit dialog
    if (showExitDialog) {
        AlertDialog(
            title = { Text("¿Está seguro de salir?") },
            text = { Text("Al salir perderás todos los cambios") },
            onDismissRequest = { showExitDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        showExitDialog = false
                        onNavigateBack()
                    }
                ) { Text("Confirmar") }
            }
        )
    }
}

@Composable
fun CityDropdown(selectedCity: String, onCitySelected: (String) -> Unit) {

    var expanded by remember { mutableStateOf(false) }
    val cities = City.values().map { it.name }

    Column(modifier = Modifier.fillMaxWidth()) {

        Text("City", style = MaterialTheme.typography.bodyMedium)

        Text(
            text = if (selectedCity.isEmpty()) "Select a city" else selectedCity,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .clickable { expanded = true }
                .padding(8.dp),
            style = MaterialTheme.typography.bodyLarge
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            cities.forEach { c ->
                DropdownMenuItem(
                    text = { Text(c) },
                    onClick = {
                        onCitySelected(c)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun PlaceTypeDropdown(selectedType: String, onTypeSelected: (String) -> Unit) {

    var expanded by remember { mutableStateOf(false) }
    val types = PlaceType.values().map { it.name }

    Column(modifier = Modifier.fillMaxWidth()) {

        Text("Category", style = MaterialTheme.typography.bodyMedium)

        Text(
            text = if (selectedType.isEmpty()) "Select a category" else selectedType,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .clickable { expanded = true }
                .padding(8.dp),
            style = MaterialTheme.typography.bodyLarge
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            types.forEach { t ->
                DropdownMenuItem(
                    text = { Text(t) },
                    onClick = {
                        onTypeSelected(t)
                        expanded = false
                    }
                )
            }
        }
    }
}
