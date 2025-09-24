package com.example.unilocal.ui.user

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.unilocal.R

@Composable
fun HomeUser () {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBarUser()
        },
        bottomBar = {
            BottomBarUser()
        }
    ) { padding ->
        ContentUser(
            padding = padding
        )
    }
}

    @Composable
    fun ContentUser(
        padding: PaddingValues
    ){

    }

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    fun TopBarUser(){
        CenterAlignedTopAppBar(
            title = {
                Text(text = stringResource(R.string.home_title))
            }
        )

    }
    @Composable
    fun BottomBarUser(){

        NavigationBar(

        ) {

            NavigationBarItem(
                label = {
                    Text(
                        text = stringResource(R.string.btn_home)
                    )
                },
                onClick = {
                    /*TODO*/
                },
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.Home,
                        contentDescription = stringResource(R.string.btn_home)
                    )
                },
                selected = true
            )

            NavigationBarItem(
                label = {
                    Text(
                        text = stringResource(R.string.btn_favorites)
                    )
                },
                onClick = {
                    /*TODO*/
                },
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.FavoriteBorder,
                        contentDescription = stringResource(R.string.btn_favorites)
                    )
                },
                selected = true
            )

            NavigationBarItem(
                label = {
                    Text(
                        text = stringResource(R.string.btn_add_place)
                    )
                },
                onClick = {
                    /*TODO*/
                },
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.AddCircle,
                        contentDescription = stringResource(R.string.btn_add_place)
                    )
                },
                selected = true
            )

        }
    }

