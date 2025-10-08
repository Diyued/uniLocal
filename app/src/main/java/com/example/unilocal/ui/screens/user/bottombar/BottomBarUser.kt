package com.example.unilocal.ui.screens.user.bottombar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.unilocal.R
import com.example.unilocal.ui.screens.user.nav.RouteTab

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
    ADD_PLACE(RouteTab.CreatePlace, R.string.btn_add_place, Icons.Outlined.AddCircle),
    //TEMPORAL
    PLACES(RouteTab.TempPlaces, R.string.btn_places, Icons.Outlined.AddCircle)
}