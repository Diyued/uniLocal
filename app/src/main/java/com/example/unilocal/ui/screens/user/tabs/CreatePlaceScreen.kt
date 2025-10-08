package com.example.unilocal.ui.screens.user.tabs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.unilocal.ui.components.CustomButton
import com.example.unilocal.ui.components.CustomTextField
import com.example.unilocal.ui.viewmodel.PlacesViewModel

@Composable
fun CreatePlaceScreen(){


    var placeName by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var schedule by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var pictures by remember { mutableStateOf("") }

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

            CustomTextField(label = "Place name", value = placeName, onValueChange = { placeName = it })
            Spacer(modifier = Modifier.height(12.dp))

            CustomTextField(label = "Address", value = address, onValueChange = { address = it })
            Spacer(modifier = Modifier.height(12.dp))

            CustomTextField(label = "Schedule", value = schedule, onValueChange = { schedule = it })
            Spacer(modifier = Modifier.height(12.dp))

            CustomTextField(label = "City", value = city, onValueChange = { city = it })
            Spacer(modifier = Modifier.height(12.dp))

            CustomTextField(label = "Category", value = category, onValueChange = { category = it })
            Spacer(modifier = Modifier.height(12.dp))

            CustomTextField(label = "Pictures", value = pictures, onValueChange = { pictures = it })
            Spacer(modifier = Modifier.height(12.dp))

            CustomButton(text = "Request", onClick = { /* TODO: Handle place creation */ })

        }
    }

}
