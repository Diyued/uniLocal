package com.example.unilocal.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.unilocal.R
import com.example.unilocal.ui.components.CustomButton
import com.example.unilocal.ui.components.CustomTextField
import com.example.unilocal.ui.viewmodel.UsersViewModel

@Composable
fun LoginScreen(
    userViewModel: UsersViewModel,
    onNavigateHome: () -> Unit,
    onRegisterClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }

    // Estados de error
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    Surface {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
        ) {
            Text(
                text = stringResource(id = R.string.txt_login_title),
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = stringResource(id = R.string.txt_login_subtitle),
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(20.dp))

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
                    Text(
                        text = emailError!!,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
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
                    Text(
                        text = passwordError!!,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Remember me + Forgot password
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = rememberMe,
                    onCheckedChange = { rememberMe = it }
                )
                Text(
                    text = stringResource(id = R.string.checkbox_remember_me),
                    modifier = Modifier.weight(1f)
                )
                TextButton(onClick = { /* TODO: Forgot Password */ }) {
                    Text(stringResource(id = R.string.txt_forgot_password))
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Login button
            CustomButton(
                text = stringResource(id = R.string.btn_login),
                onClick = {
                    val userLogged = userViewModel.login(email, password)
                    var hasError = false

                    if (email.isBlank()) {
                        emailError = "Email is required"
                        hasError = true
                    } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        emailError = "Invalid email format"
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
                        if (userLogged != null)
                        {
                            onNavigateHome()
                        } else {
                            passwordError = "Invalid email or password"
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Sign up button
            TextButton(onClick = { onRegisterClick() }) {
                Text(
                    text = "${stringResource(id = R.string.txt_no_account)} ${stringResource(id = R.string.btn_sign_up)}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
