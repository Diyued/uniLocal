package com.example.unilocal.ui.user

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.unilocal.R
import kotlinx.coroutines.launch

@Composable
fun HomeUser() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(text = "Perfil")

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
            bottomBar = {
                BottomBarUser()
            }
        ) { padding ->
            ContentUser(padding = padding)
        }
    }
}

@Composable
fun ContentUser(padding: PaddingValues) {
    // Aquí puedes poner tu contenido o NavHost
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarUser(onMenuClick: () -> Unit) {
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

@Composable
fun BottomBarUser() {
    NavigationBar {
        NavigationBarItem(
            label = { Text(text = stringResource(R.string.btn_home)) },
            onClick = { /*TODO*/ },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Home,
                    contentDescription = stringResource(R.string.btn_home)
                )
            },
            selected = true
        )

        NavigationBarItem(
            label = { Text(text = stringResource(R.string.btn_favorites)) },
            onClick = { /*TODO*/ },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.FavoriteBorder,
                    contentDescription = stringResource(R.string.btn_favorites)
                )
            },
            selected = false
        )

        NavigationBarItem(
            label = { Text(text = stringResource(R.string.btn_add_place)) },
            onClick = { /*TODO*/ },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.AddCircle,
                    contentDescription = stringResource(R.string.btn_add_place)
                )
            },
            selected = false
        )
    }
}
