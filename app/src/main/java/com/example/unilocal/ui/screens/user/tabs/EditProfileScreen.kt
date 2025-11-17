package com.example.unilocal.ui.screens.user.tabs

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.unilocal.model.City
import com.example.unilocal.ui.components.CustomButton
import com.example.unilocal.ui.components.CustomTextField
import com.example.unilocal.ui.components.InputText
import com.example.unilocal.ui.nav.LocalMainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    onConfirmClick: () -> Unit,
    userId: String?
) {
    val usersViewModel = LocalMainViewModel.current.usersViewModel
    val currentUser by usersViewModel.currentUser.collectAsState()


    var fullName by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(userId) {
        if (userId != null) {
            usersViewModel.findById(userId)
        }
    }

    val user = currentUser

    LaunchedEffect(user) {
        user?.let {
            fullName = it.name
            username = it.username
            email = it.email
            city = it.city.displayName
        }
    }


    // Estados de error
    var fullNameError by remember { mutableStateOf<String?>(null) }
    var usernameError by remember { mutableStateOf<String?>(null) }
    var cityError by remember { mutableStateOf<String?>(null) }

    Surface {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Text(
                text = "Edit your info",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "Make sure you edit every field that you need",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Full Name
            Column(modifier = Modifier.fillMaxWidth()) {
                CustomTextField(
                    label = "Full Name *",
                    value = fullName,
                    onValueChange = {
                        fullName = it
                        fullNameError = null
                    }
                )
                if (fullNameError != null) {
                    Text(
                        text = fullNameError!!,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Username
            Column(modifier = Modifier.fillMaxWidth()) {
                CustomTextField(
                    label = "Username *",
                    value = username,
                    onValueChange = {
                        username = it
                        usernameError = null
                    }
                )
                if (usernameError != null) {
                    Text(
                        text = usernameError!!,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Email (inhabilitado)
            Column(modifier = Modifier.fillMaxWidth()) {
                InputText(
                    label = "Email",
                    value = email,
                    onValueChange = { },
                    supportingText = "",
                    onValidate = { false },
                    enabled = false
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // City
            var expanded by remember { mutableStateOf(false) }
            val cities = remember { City.values().map { it.displayName } }
            Column(modifier = Modifier.fillMaxWidth()){
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    TextField(
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),
                        value = city,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("City *") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        cities.forEach { selectionOption ->
                            DropdownMenuItem(
                                text = { Text(selectionOption) },
                                onClick = {
                                    city = selectionOption
                                    expanded = false
                                    cityError = null
                                }
                            )
                        }
                    }
                }
                if (cityError != null) {
                    Text(
                        text = cityError!!,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }


            Spacer(modifier = Modifier.height(12.dp))

            // Password (inhabilitado)
            Column(modifier = Modifier.fillMaxWidth()) {
                InputText(

                    label = "Password",
                    value = password,
                    onValueChange = { },
                    isPassword = true,
                    supportingText = "",
                    onValidate = { false },
                    enabled = false

                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Confirm button
            CustomButton(
                text = "Confirm",
                onClick = {
                    var hasError = false

                    if (fullName.isBlank()) {
                        fullNameError = "Full name is required"
                        hasError = true
                    }
                    if (username.isBlank()) {
                        usernameError = "Username is required"
                        hasError = true
                    }
                    if (city.isBlank()) {
                        cityError = "City is required"
                        hasError = true
                    }

                    if (!hasError) {
                        val selectedCity = City.values().find{ it.displayName == city}
                        if (selectedCity != null) {
                            usersViewModel.update(
                                user!!.copy(
                                    name = fullName,
                                    username = username,
                                    city = selectedCity
                                )
                            )
                            onConfirmClick()
                        }
                    }
                }
            )
        }
    }
}