package com.example.unilocal.ui.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost

import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.unilocal.model.Role
import com.example.unilocal.ui.screens.LoginScreen
import com.example.unilocal.ui.screens.RegisterScreen
import com.example.unilocal.ui.config.RouteScreen
import com.example.unilocal.ui.screens.PasswordResetCodeScreen
import com.example.unilocal.ui.screens.PasswordResetScreen
import com.example.unilocal.ui.screens.admin.HomeAdmin
import com.example.unilocal.ui.screens.admin.tabs.PlaceValidationScreen

import com.example.unilocal.ui.screens.user.HomeUser
import com.example.unilocal.ui.viewmodel.MainViewModel
import com.example.unilocal.ui.viewmodel.UsersViewModel
import com.example.unilocal.utils.SharedPrefsUtil

val LocalMainViewModel = androidx.compose.runtime.compositionLocalOf<MainViewModel> {error("MainViewModel not found")}
@Composable
fun Navigation(
    mainViewModel: MainViewModel
) {
    val context = LocalContext.current
    val navController = rememberNavController()
    val user = SharedPrefsUtil.getPreference(context)

    val startDestination = if(user.isEmpty()){
        RouteScreen.Login
    }else{
        if(user["rol"] == "ADMIN"){
            RouteScreen.HomeAdmin
        }else{
            RouteScreen.HomeUser
        }
    }

    CompositionLocalProvider(
        LocalMainViewModel provides mainViewModel
    ) {


    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<RouteScreen.Login> {
            LoginScreen(
                usersViewModel = mainViewModel.usersViewModel,
                onNavigateHome = {userId, role ->
                    SharedPrefsUtil.savePreference(context, userId, role)
                    if(role == Role.ADMIN){
                        navController.navigate(RouteScreen.HomeAdmin)
                    }else{
                        navController.navigate(RouteScreen.HomeUser)
                    }
                },

                onRegisterClick = {
                    navController.navigate(RouteScreen.Register)
                },
                onNavigateToPasswordReset = {
                    navController.navigate(RouteScreen.PasswordReset)
                }

            )
        }

        composable<RouteScreen.Register> {
            RegisterScreen(
                usersViewModel = mainViewModel.usersViewModel,
                onLoginClick = {

                    navController.navigate(RouteScreen.Login)
                }
            )
        }

        composable<RouteScreen.HomeUser> {
            HomeUser(
                userId = user["userId"]!!,
                mainViewModel = mainViewModel,
                logout = {
                    SharedPrefsUtil.clearPreference(context)
                    navController.navigate(RouteScreen.Login)
                }
            )
        }

        composable<RouteScreen.HomeAdmin> {
            HomeAdmin(
                mainViewModel = mainViewModel,
                logout = {
                    SharedPrefsUtil.clearPreference(context)
                    navController.navigate(RouteScreen.Login)
                }

            )
        }

        composable<RouteScreen.PasswordReset> {
            PasswordResetScreen(
                usersViewModel = mainViewModel.usersViewModel,
                onNavigateResetCode = {userId ->
                    navController.navigate(RouteScreen.PasswordResetCode(userId))

                }
            )
        }

        composable<RouteScreen.PasswordResetCode> { backStackEntry ->
            val args: RouteScreen.PasswordResetCode = backStackEntry.toRoute()
            val userId = args.userId

            PasswordResetCodeScreen(
                usersViewModel = mainViewModel.usersViewModel,
                userId = userId,
                onNavigateBack = {
                    navController.navigate(RouteScreen.Login)
                }
            )
        }



    }
    }
}