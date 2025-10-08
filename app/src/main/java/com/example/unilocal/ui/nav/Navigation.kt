package com.example.unilocal.ui.nav

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost

import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.unilocal.ui.screens.user.tabs.CreatePlaceScreen
import com.example.unilocal.ui.screens.user.tabs.EditProfileScreen
import com.example.unilocal.ui.screens.LoginScreen
import com.example.unilocal.ui.screens.RegisterScreen
import com.example.unilocal.ui.config.RouteScreen

import com.example.unilocal.ui.screens.user.HomeUser
import com.example.unilocal.ui.screens.user.nav.RouteTab
import com.example.unilocal.ui.viewmodel.UsersViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val usersViewModel: UsersViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = RouteScreen.Login
    ) {
        composable<RouteScreen.Login> {
            LoginScreen(
                usersViewModel,
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
                usersViewModel,
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



    }
}