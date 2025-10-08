package com.example.unilocal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.unilocal.ui.theme.UniLocalTheme
import com.example.unilocal.ui.nav.Navigation
import com.example.unilocal.ui.viewmodel.MainViewModel
import com.example.unilocal.ui.viewmodel.PlacesViewModel
import com.example.unilocal.ui.viewmodel.ReviewsViewModel
import com.example.unilocal.ui.viewmodel.UsersViewModel



class MainActivity : ComponentActivity() {

    private val usersViewModel: UsersViewModel by viewModels()
    private val reviewsViewModel: ReviewsViewModel by viewModels()
    private val placesViewModel: PlacesViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mainViewModel = MainViewModel(
            placesViewModel = placesViewModel,
            reviewsViewModel = reviewsViewModel,
            usersViewModel = usersViewModel
        )
        setContent (
            content = {
                UniLocalTheme {
                    Navigation(
                        mainViewModel = mainViewModel
                    )
                }
            }
        )
    }
}