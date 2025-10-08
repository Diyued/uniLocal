package com.example.unilocal.ui.screens.user.tabs

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.unilocal.ui.components.CustomButton
import com.example.unilocal.ui.components.CustomTextField

@Composable
fun EditProfileScreen(
    onConfirmClick: () -> Unit
) {
    var fullName by remember { mutableStateOf("John Doe") }
    var username by remember { mutableStateOf("johndoe123") }
    var email by remember { mutableStateOf("johndoe@gmail.com") }
    var city by remember { mutableStateOf("Los Angeles, CA") }
    var password by remember { mutableStateOf("123456") }

    // Estados de error
    var fullNameError by remember { mutableStateOf<String?>(null) }
    var usernameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var cityError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    Surface {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
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

            // Email
            Column(modifier = Modifier.fillMaxWidth()) {
                CustomTextField(
                    label = "Email",
                    value = email,
                    onValueChange = {
                        email = it
                        emailError = null
                    }
                )
                if (emailError != null) {
                    Text(
                        text = emailError!!,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
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

            // Password
            Column(modifier = Modifier.fillMaxWidth()) {
                CustomTextField(
                    label = "Password",
                    value = password,
                    onValueChange = {
                        password = it
                        passwordError = null
                    },
                    isPassword = true
                )
                if (passwordError != null) {
                    Text(
                        text = passwordError!!,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
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
                    if (password.isBlank()) {
                        passwordError = "Password is required"
                        hasError = true
                    } else if (password.length < 6) {
                        passwordError = "Password must be at least 6 characters"
                        hasError = true
                    }

                    if (!hasError) {
                        onConfirmClick()
                    }
                }
            )
        }
    }
}