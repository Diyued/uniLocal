package com.example.unilocal.ui.screens.user.tabs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.unilocal.ui.nav.LocalMainViewModel
import com.example.unilocal.ui.viewmodel.MainViewModel
import com.example.unilocal.ui.viewmodel.PlacesViewModel

@Composable
fun PlaceDetail(
    placesViewModel: PlacesViewModel,
    id: String
){
    val place = LocalMainViewModel.current.placesViewModel.findbyID(id)


        Text(
            text = place!!.title
        )

}