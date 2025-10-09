package com.example.unilocal.ui.screens.admin.tabs

import android.widget.Toast
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.error
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.unilocal.model.Place
import com.example.unilocal.ui.viewmodel.PlacesViewModel

@Composable
fun PlaceValidationScreen(
    padding: PaddingValues,
    placesViewModel: PlacesViewModel,
    placeId: String,
    onNavigateBack: () -> Unit
){

    var place by remember { mutableStateOf<Place?>(null) }
    val context = LocalContext.current


    LaunchedEffect(placeId) {
        place = placesViewModel.findByID(placeId)
    }

    if (place == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()) // Para que sea scrollable si el contenido es largo
        ) {

            Text(
                text = place!!.title,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))


            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(place!!.images) // Asumiendo que `images` es una URL String
                    .crossfade(true)
                    .build(),
                contentDescription = "Imagen de ${place!!.title}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Descripción
            InfoRow(label = "Descripción", value = place!!.description)
            InfoRow(label = "Dirección", value = place!!.address)
            InfoRow(label = "Teléfonos", value = place!!.phones)
            InfoRow(label = "Categoría", value = place!!.type) // Asumiendo que type es un String o Enum

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Botón Rechazar
                Button(
                    onClick = {
                        placesViewModel.rejectPlace(placeId)
                        onNavigateBack()
                        Toast.makeText(context, "Lugar rechazado", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Icon(Icons.Default.Close, contentDescription = "Rechazar")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Rechazar")
                }

                // Botón Aprobar
                Button(
                    onClick = {
                        placesViewModel.approvePlace(placeId)
                        onNavigateBack()
                        Toast.makeText(context, "Lugar aprobado", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Icon(Icons.Default.Check, contentDescription = "Aprobar")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Aprobar")
                }
            }
        }
    }

}

@Composable
private fun InfoRow(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(text = value, style = MaterialTheme.typography.bodyLarge)
        HorizontalDivider(modifier = Modifier.padding(top = 8.dp))
    }
}