package com.example.unilocal.ui.screens.user.tabs

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.unilocal.ui.components.CustomButton
import com.example.unilocal.ui.components.CustomTextField
import com.example.unilocal.ui.components.InputText
import com.example.unilocal.ui.nav.LocalMainViewModel

@Composable
fun EditProfileScreen(
    onConfirmClick: () -> Unit,
    userId: String?
) {
    val usersViewModel = LocalMainViewModel.current.usersViewModel
    val user = usersViewModel.findbyID(userId?:"")

    var fullName by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(user) {
        user?.let {
            fullName = it.name
            username = it.username
            email = it.email
            city = it.city
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
            Column(modifier = Modifier.fillMaxWidth()) {
                CustomTextField(
                    label = "City *",
                    value = city,
                    onValueChange = {
                        city = it
                        cityError = null
                    }
                )
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
                        usersViewModel.update(
                            user!!.copy(
                                name = fullName,
                                username = username,
                                city = city
                            )
                        )
                        onConfirmClick()
                    }
                }
            )
        }
    }
}
