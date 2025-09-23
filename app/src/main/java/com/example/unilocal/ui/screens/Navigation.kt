package com.example.unilocal.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost

import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.unilocal.ui.theme.UniLocalTheme
@Composable
fun Navigation() {
    val navController = rememberNavController()

    Surface (modifier = Modifier.fillMaxSize()) {
        NavHost(
            navController = navController,
            startDestination = RouteScreen.Auth
        ) {
            composable<RouteScreen.Auth> {
                LoginScreen(
                    onLoginClick = {
                        navController.navigate(RouteScreen.Home)
                    }

                )
            }


        }
    }
}