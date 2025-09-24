package com.example.unilocal.ui.user

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.unilocal.R
import kotlinx.coroutines.launch
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.unilocal.ui.screens.CreatePlaceScreen
import com.example.unilocal.ui.screens.Map
import com.example.unilocal.ui.nav.RouteTab

@Composable
fun HomeUser() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()

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
                BottomBarUser(
                    navController = navController
                )
            }
        ) { padding ->
            ContentUser(
                navController = navController,
                padding = padding
            )
        }
    }
}

@Composable
fun ContentUser(
    padding: PaddingValues,
    navController: NavHostController
) {

    NavHost(
        modifier = Modifier.padding(padding),
        navController = navController,
        startDestination = RouteTab.Map
    ){
        composable<RouteTab.Map> {
            Map()
        }

        composable<RouteTab.Favorites> {
            //FavoritesScreen()
        }

        composable<RouteTab.CreatePlace> {
            CreatePlaceScreen()
        }
    }

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
fun BottomBarUser(
    navController: NavHostController
) {

    val startDestination = Destination.HOME
    var selectedDestination by rememberSaveable { mutableIntStateOf(startDestination.ordinal) }

    NavigationBar {

        Destination.entries.forEachIndexed { index, destination ->
            NavigationBarItem(
                label = {
                    Text(
                        text = stringResource(destination.label)
                    )
                },
                onClick = {
                    selectedDestination = index
                    navController.navigate(destination.route)
                },
                icon = {
                    Icon(
                        imageVector = destination.icon,
                        contentDescription = stringResource(destination.label)
                    )
                },
                selected = selectedDestination == index
            )
        }
    }
}

enum class Destination(
    val route: RouteTab,
    val label: Int,
    val icon: ImageVector,
){
    HOME(RouteTab.Map, R.string.btn_home, Icons.Outlined.Home),
    FAVORITES(RouteTab.Favorites, R.string.btn_favorites, Icons.Outlined.FavoriteBorder),
    ADD_PLACE(RouteTab.CreatePlace, R.string.btn_add_place, Icons.Outlined.AddCircle)
}
