package com.example.unilocal.ui.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost

import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.unilocal.ui.screens.LoginScreen
import com.example.unilocal.ui.screens.RegisterScreen
import com.example.unilocal.ui.config.RouteScreen
import com.example.unilocal.ui.screens.admin.HomeAdmin

import com.example.unilocal.ui.screens.user.HomeUser
import com.example.unilocal.ui.viewmodel.MainViewModel
import com.example.unilocal.ui.viewmodel.UsersViewModel

val LocalMainViewModel = androidx.compose.runtime.compositionLocalOf<MainViewModel> {error("MainViewModel not found")}
@Composable
fun Navigation(
    mainViewModel: MainViewModel
) {
    val navController = rememberNavController()
    val usersViewModel: UsersViewModel = viewModel()

    CompositionLocalProvider(
        LocalMainViewModel provides mainViewModel
    ) {


    NavHost(
        navController = navController,
        startDestination = RouteScreen.Login
    ) {
        composable<RouteScreen.Login> {
            LoginScreen(
                usersViewModel,
                onNavigateHomeUser = {
                    navController.navigate(RouteScreen.HomeUser)
                },

                onNavigateHomeAdmin = {
                    navController.navigate(RouteScreen.HomeAdmin)
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

        composable<RouteScreen.HomeUser> {
            HomeUser(

                //onNavigateCreatePlace = {
                //    navController.navigate(RouteScreen.CreatePlace)
                //}
            )
        }

        composable<RouteScreen.HomeAdmin> {
            HomeAdmin(

            )
        }


    }
    }
}