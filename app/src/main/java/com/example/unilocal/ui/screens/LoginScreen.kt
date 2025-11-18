package com.example.unilocal.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.unilocal.R
import com.example.unilocal.model.Role
import com.example.unilocal.ui.components.CustomButton
import com.example.unilocal.ui.components.CustomTextField
import com.example.unilocal.ui.components.OperationResultHandler
import com.example.unilocal.ui.nav.LocalMainViewModel
import com.example.unilocal.ui.viewmodel.UsersViewModel

@Composable
fun LoginScreen(
    usersViewModel: UsersViewModel,
    onNavigateHome: (String, Role) -> Unit,
    onRegisterClick: () -> Unit,
    onNavigateToPasswordReset: () -> Unit
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    val userResult by usersViewModel.userResult.collectAsState()


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
            
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextButton(onClick = {
                    onNavigateToPasswordReset()
                }) {
                    Text(stringResource(id = R.string.txt_forgot_password))
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Login button
            CustomButton(
                text = stringResource(id = R.string.btn_login),
                onClick = {
                    usersViewModel.login(email, password)


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

            OperationResultHandler(
                result = userResult,
                onSuccess = {
                    usersViewModel.currentUser.value?.let { user ->
                        onNavigateHome(user.id, user.role)
                    }
                    usersViewModel.resetOperationResult()
                },
                onFailure = {
                    Toast.makeText(context, "Error de inicio de sesión. Por favor, revisa tus credenciales.", Toast.LENGTH_LONG).show()
                    usersViewModel.resetOperationResult()

                }
            )
        }
    }
}
