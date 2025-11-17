package com.example.unilocal.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.unilocal.R
import com.example.unilocal.model.City
import com.example.unilocal.model.Role
import com.example.unilocal.model.User
import com.example.unilocal.ui.components.CustomButton
import com.example.unilocal.ui.components.CustomTextField
import com.example.unilocal.ui.components.OperationResultHandler
import com.example.unilocal.ui.nav.LocalMainViewModel
import com.example.unilocal.ui.viewmodel.MainViewModel
import com.example.unilocal.ui.viewmodel.UsersViewModel

@Composable
fun RegisterScreen(
    usersViewModel: UsersViewModel,
    onLoginClick: () -> Unit
) {


    var name by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val userResult by usersViewModel.userResult.collectAsState()

    // Estados de error
    var nameError by remember { mutableStateOf<String?>(null) }
    var usernameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var cityError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.txt_register_title),
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = stringResource(id = R.string.txt_register_subtitle),
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Full name
            Column(modifier = Modifier.fillMaxWidth()) {
                CustomTextField(
                    label = stringResource(id = R.string.txt_full_name),
                    value = name,
                    onValueChange = {
                        name = it
                        nameError = null
                    }
                )
                if (nameError != null) {
                    Text(nameError!!, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Username
            Column(modifier = Modifier.fillMaxWidth()) {
                CustomTextField(
                    label = stringResource(id = R.string.txt_username),
                    value = username,
                    onValueChange = {
                        username = it
                        usernameError = null
                    }
                )
                if (usernameError != null) {
                    Text(usernameError!!, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Email
            Column(modifier = Modifier.fillMaxWidth()) {
                CustomTextField(
                    label = stringResource(id = R.string.txt_email),
                    value = email,
                    onValueChange = {
                        email = it
                        emailError = null
                    }
                )
                if (emailError != null) {
                    Text(emailError!!, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // City
            Column(modifier = Modifier.fillMaxWidth()) {
                CustomTextField(
                    label = stringResource(id = R.string.txt_city),
                    value = city,
                    onValueChange = {
                        city = it
                        cityError = null
                    }
                )
                if (cityError != null) {
                    Text(cityError!!, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Password
            Column(modifier = Modifier.fillMaxWidth()) {
                CustomTextField(
                    label = stringResource(id = R.string.txt_password),
                    value = password,
                    onValueChange = {
                        password = it
                        passwordError = null
                    },
                    isPassword = true
                )
                if (passwordError != null) {
                    Text(passwordError!!, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Sign Up button
            CustomButton(
                text = stringResource(id = R.string.btn_sign_up),
                onClick = {
                    val user = User(
                        name = name,
                        username = username,
                        email = email,
                        role = Role.USER,
                        city = City.ARMENIA,
                        password = password
                    )
                    usersViewModel.create(user)

                    var hasError = false

                    if (name.isBlank()) {
                        nameError = "Full name is required"
                        hasError = true
                    }
                    if (username.isBlank()) {
                        usernameError = "Username is required"
                        hasError = true
                    }
                    if (email.isBlank()) {
                        emailError = "Email is required"
                        hasError = true
                    } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        emailError = "Invalid email format"
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
                        // TODO: Handle sign up (ej: enviar a backend o navegar)
                    }
                }
            )
            OperationResultHandler(
                result = userResult,
                onSuccess = {
                    onLoginClick()
                    usersViewModel.resetOperationResult()
                },
                onFailure = {
                    usersViewModel.resetOperationResult()

                }

            )


            Spacer(modifier = Modifier.height(20.dp))

            TextButton(onClick = { onLoginClick() }) {
                Text(
                    text = stringResource(id = R.string.txt_already_have_account),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
