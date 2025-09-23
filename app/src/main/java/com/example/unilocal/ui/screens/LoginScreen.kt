package com.example.unilocal.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.unilocal.ui.components.CustomButton
import com.example.unilocal.ui.components.CustomTextField

@Composable
fun LoginScreen(onLoginClick: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Log in to your Account", style = MaterialTheme.typography.titleLarge)
        Text("Welcome back, please enter your details.", style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(20.dp))

        CustomTextField(label = "Email Address", value = email, onValueChange = { email = it })
        Spacer(modifier = Modifier.height(12.dp))
        CustomTextField(label = "Password", value = password, onValueChange = { password = it }, isPassword = true)

        Spacer(modifier = Modifier.height(12.dp))

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            Checkbox(checked = rememberMe, onCheckedChange = { rememberMe = it })
            Text("Remember me", modifier = Modifier.weight(1f))
            TextButton(onClick = { /* TODO: Forgot Password */ }) {
                Text("Forgot Password?")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        CustomButton(text = "Log in", onClick = onLoginClick)

        Spacer(modifier = Modifier.height(20.dp))

        Text("Don’t have an account? Sign Up", style = MaterialTheme.typography.bodySmall)
    }
}
