package com.example.unilocal.ui.screens.admin

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.unilocal.ui.screens.admin.bottombar.BottomBarAdmin
import com.example.unilocal.ui.screens.admin.nav.ContentAdmin

@Composable
fun HomeAdmin(

) {
    val navController = rememberNavController()

    Scaffold (
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomBarAdmin(
                navController = navController,
            )
        }
    ) { padding ->
        ContentAdmin(
            padding = padding,
            navController = navController
        )
    }
}