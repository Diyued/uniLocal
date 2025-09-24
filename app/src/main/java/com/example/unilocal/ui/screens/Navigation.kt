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
import com.example.unilocal.ui.user.HomeUser

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = RouteScreen.Login
    ) {
        composable<RouteScreen.Login> {
            LoginScreen(
                onNavigateHome = {
                    navController.navigate(RouteScreen.Home)
                },

                onRegisterClick = {
                    navController.navigate(RouteScreen.Register)
                }
            )
        }

        composable<RouteScreen.Register> {
            RegisterScreen(
                onLoginClick = {
                    navController.navigate(RouteScreen.Login)
                }
            )
        }

        composable<RouteScreen.Home> {
            HomeUser(
                //onNavigateCreatePlace = {
                //    navController.navigate(RouteScreen.CreatePlace)
                //}
            )
        }

        composable<RouteScreen.CreatePlace> {
            CreatePlaceScreen()
        }
    }
}