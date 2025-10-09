package com.example.unilocal.ui.screens.admin

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.rememberNavController
import com.example.unilocal.R
import com.example.unilocal.ui.screens.admin.bottombar.BottomBarAdmin
import com.example.unilocal.ui.screens.admin.nav.ContentAdmin
import com.example.unilocal.ui.screens.user.TopBarUser
import com.example.unilocal.ui.viewmodel.MainViewModel


@Composable
fun HomeAdmin(
    mainViewModel: MainViewModel,
    logout: () -> Unit
) {
    val navController = rememberNavController()

    Scaffold (
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBarUser(
                logout = logout
            )
        },
                bottomBar = {
                    BottomBarAdmin(
                        navController = navController,
                    )
                }
            ) { padding ->
                ContentAdmin(
                    mainViewModel = mainViewModel,
                    padding = padding,
                    navController = navController
                )
            }
        }



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarUser(
    logout: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.home_title))
                },
        actions = {
            IconButton(onClick = logout) {
                Icon(
                    imageVector = Icons.Default.ExitToApp,
                    contentDescription = null
                )
            }
        }
    )
}