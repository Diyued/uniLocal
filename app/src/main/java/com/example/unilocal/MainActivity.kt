package com.example.unilocal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.unilocal.ui.screens.LoginScreen
import com.example.unilocal.ui.theme.UniLocalTheme
import com.example.unilocal.ui.screens.Navigation


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UniLocalTheme {
                LoginScreen(
                    onLoginClick = {
                        // Aquí por ahora solo mostramos un log o navegas
                        println("Login clicked 🚀")
                    }
                )

            }
        }
    }
}