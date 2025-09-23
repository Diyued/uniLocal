package com.example.unilocal.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.unilocal.ui.components.CustomButton
import com.example.unilocal.ui.components.CustomTextField

@Composable
fun RegisterScreen () {

    var name by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text("Create an Account", style = MaterialTheme.typography.titleLarge)
        Text("Sign up now to get started with an account.", style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(20.dp))

        CustomTextField(label = "Full name", value = name, onValueChange = { name = it })
        Spacer(modifier = Modifier.height(12.dp))

        CustomTextField(label = "Username", value = username, onValueChange = { username = it })
        Spacer(modifier = Modifier.height(12.dp))

        CustomTextField(label = "Email", value = email, onValueChange = { email = it })
        Spacer(modifier = Modifier.height(12.dp))

        CustomTextField(label = "City", value = city, onValueChange = { city = it })
        Spacer(modifier = Modifier.height(12.dp))

        CustomTextField(label = "Password", value = password, onValueChange = { password = it }, isPassword = true)
        Spacer(modifier = Modifier.height(12.dp))

        CustomButton(text = "Sign Up", onClick = { /* TODO: Handle sign up */ })

        TextButton(onClick = { /* TODO: Reset password */ }) {
            Text("Already have an account? Log in")
        }
    }

}