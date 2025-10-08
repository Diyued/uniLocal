package com.example.unilocal.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.example.unilocal.model.Place
import com.example.unilocal.ui.nav.LocalMainViewModel

@Composable
fun PlacesList(
    places: List<Place>,
    padding: PaddingValues,
    onNavigateToPlaceDetail: (String) -> Unit){

        LazyColumn(
            modifier = Modifier
                .padding(paddingValues = padding)) {
            items(places){
                ListItem(
                    modifier = Modifier.clip(MaterialTheme.shapes.small).clickable{
                        onNavigateToPlaceDetail(it.id)
                    },
                    headlineContent = {
                        Text(text = it.title)
                    },
                    supportingContent = {

                        Text(text = it.description)
                    }
                )


            }
        }
}