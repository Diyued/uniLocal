package com.example.unilocal.ui.screens.user

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.unilocal.R
import kotlinx.coroutines.launch
import com.example.unilocal.ui.screens.user.bottombar.BottomBarUser
import com.example.unilocal.ui.screens.user.nav.ContentUser
import com.example.unilocal.ui.screens.user.nav.RouteTab
import com.example.unilocal.ui.viewmodel.MainViewModel

@Composable
fun HomeUser(
    mainViewModel: MainViewModel,
    logout: () -> Unit
) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()

    var showFAB by remember { mutableStateOf(false) }
    var showTapBar by remember { mutableStateOf(true) }
    var title by remember { mutableStateOf(0) }
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet (modifier = Modifier.fillMaxWidth(0.7f)){
                Column {
                    Text(
                        text = "Perfil",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clickable() {
                                scope.launch { drawerState.close() }
                                navController.navigate(RouteTab.EditProfileScreen)
                            }
                    )
                    Text(
                        text = "Your Places",
                                modifier = Modifier
                                .fillMaxWidth()
                            .padding(16.dp)
                            .clickable() {
                                scope.launch { drawerState.close() }
                                navController.navigate(RouteTab.Places)
                            }
                    )
                    Text(
                        text = "Cerrar Sesión",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clickable() {
                                scope.launch { drawerState.close() }
                                logout()
                            }
                    )
                }

            }
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopBarUser(
                    onMenuClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            },
            floatingActionButton = {
                if (showFAB) {
                    FloatingActionButton(
                        onClick = {
                            navController.navigate(RouteTab.CreatePlace)
                        },
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Add"
                        )
                    }
                }
            },
            bottomBar = {
                BottomBarUser(
                    navController = navController,
                    showTapBar = {
                        showTapBar = it
                    },
                    showFAB = {
                        showFAB = it
                    },
                    titleTopBar = {
                        title = it
                    }
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                ContentUser(
                    setShowFAB = {
                        showFAB = it
                    },
                    navController = navController,
                    padding = PaddingValues(0.dp),
                    mainViewModel = mainViewModel
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarUser(
    onMenuClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = { Text(text = stringResource(R.string.home_title)) },
        navigationIcon = {
            IconButton(onClick = onMenuClick) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menú"
                )
            }
        }
    )
}




