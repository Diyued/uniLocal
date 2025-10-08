package com.example.unilocal.ui.screens.admin.bottombar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Place
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
import com.example.unilocal.ui.screens.admin.nav.RouteTab

@Composable
fun BottomBarAdmin(
    navController: NavHostController,

){

    val startDestination = Destination.PENDING_PLACES
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
    PENDING_PLACES(RouteTab.PendingPlaces, R.string.btn_pending_places, Icons.Outlined.Place),
    REVIEWED_PLACES(RouteTab.ReviewedPlaces, R.string.btn_reviewed_places, Icons.Outlined.CheckCircle)
}