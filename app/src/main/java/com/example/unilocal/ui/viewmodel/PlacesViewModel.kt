package com.example.unilocal.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.unilocal.model.Location
import com.example.unilocal.model.Place
import com.example.unilocal.model.PlaceType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PlacesViewModel: ViewModel() {
    private val _places = MutableStateFlow(emptyList<Place>())
    val places: StateFlow<List<Place>> = _places.asStateFlow()
    init {
        loadPlaces()
    }
    fun loadPlaces() {
        _places.value = listOf(
            Place(
                id = "1",
                title = "Restaurante El paisa",
                description = "El mejor restaurante paisa",
                address = "Cra 12 # 12 - 12",
                location = "1.23, 2.34",
                images = "listOf(https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTM3Uv-2p7sm5kdLvUgjiDXwsdrtkYi7XTFfQ&s)",
                phones = "3123132133, 3123132133",
                type = "PlaceType.RESTAURANT",
                schedule = "listOf()"
            ),
            Place(
                id = "2",
                title = "Bar test",
                description = "Un bar test",
                address = "Calle 12 # 12 - 12",
                location = "1.23, 2.34",
                images = "listOf(https://dynamic-media-cdn.tripadvisor.com/media/photo-o/19/aa/b9/fa/caption.jpg?w=900&h=500&s=1)",
                phones = "3123132133 , 3123132133",
                type = "PlaceType.BAR",
                schedule = "listOf()"
            )
        )
    }
    fun create(place: Place) {
        _places.value = _places.value + place
    }
    fun findbyID(id: String): Place? {
        return _places.value.find { it.id == id }
    }
    /*fun findByType(type: PlaceType): List<Place> {
        return _places.value.filter { it.type == type }
    }
    */

    fun findyName(name: String): List<Place> {
        return _places.value.filter { it.title.contains(name) }

    }


}