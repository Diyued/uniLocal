package com.example.unilocal.ui.viewmodel

import androidx.activity.result.launch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unilocal.model.City
import com.example.unilocal.model.Location
import com.example.unilocal.model.Place
import com.example.unilocal.model.PlaceStatus
import com.example.unilocal.model.PlaceType
import com.example.unilocal.model.Schedule
import com.example.unilocal.utils.RequestResult
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.time.DayOfWeek
import java.time.LocalTime

class PlacesViewModel: ViewModel() {
    private val _places = MutableStateFlow(emptyList<Place>())
    val places: StateFlow<List<Place>> = _places.asStateFlow()

    private val _approvedPlaces = MutableStateFlow<List<Place>>(emptyList())
    val approvedPlaces: StateFlow<List<Place>> = _approvedPlaces.asStateFlow()

    private val _rejectedPlaces = MutableStateFlow<List<Place>>(emptyList())
    val rejectedPlaces: StateFlow<List<Place>> = _rejectedPlaces.asStateFlow()

    private val _pendingPlaces = MutableStateFlow<List<Place>>(emptyList())
    val pendingPlaces: StateFlow<List<Place>> = _pendingPlaces.asStateFlow()

    private val _reviewedPlaces = MutableStateFlow<List<Place>>(emptyList())
    val reviewedPlaces: StateFlow<List<Place>> = _reviewedPlaces.asStateFlow()

    val db = Firebase.firestore
    private val _placeResult = MutableStateFlow<RequestResult?>(null)
    val placeResult: StateFlow<RequestResult?> = _placeResult.asStateFlow()

    private val _currentPlace = MutableStateFlow<Place?>(null)
    val currentPlace: StateFlow<Place?> = _currentPlace.asStateFlow()


    init {
        loadPlaces()
    }


    fun create(place: Place) {
        viewModelScope.launch {
            _placeResult.value = RequestResult.Loading
            _placeResult.value = runCatching { createFirebase(place) }
                .fold(
                    onSuccess = {
                        RequestResult.Success("Lugar creado Correctamente"
                        )
                    },
                    onFailure = {
                        RequestResult.Failure(it.message ?: "Error creando lugar"
                        )
                    }
                )
        }
    }
    private suspend fun createFirebase(place: Place) { //funcion async
        db.collection("places")
            .add(place)
            .await()
    }


    fun approvePlace(placeId: String) {
        val currentPlaces = _places.value
        val placeToUpdate = currentPlaces.find { it.id == placeId }

        if (placeToUpdate != null) {
            val updatedPlace = placeToUpdate.copy(status = PlaceStatus.APPROVED)
            _places.value = currentPlaces.map {
                if (it.id == placeId) updatedPlace else it
            }
            updateFilteredLists()
        }
    }


    fun rejectPlace(placeId: String) {
        val currentPlaces = _places.value
        val placeToUpdate = currentPlaces.find { it.id == placeId }

        if (placeToUpdate != null) {
            val updatedPlace = placeToUpdate.copy(status = PlaceStatus.REJECTED)
            _places.value = currentPlaces.map {
                if (it.id == placeId) updatedPlace else it
            }
            updateFilteredLists()
        }
    }

    private fun updateFilteredLists() {
        _approvedPlaces.value = _places.value.filter { it.status == PlaceStatus.APPROVED }
        _pendingPlaces.value = _places.value.filter { it.status == PlaceStatus.PENDING }
        _rejectedPlaces.value = _places.value.filter { it.status == PlaceStatus.REJECTED }
        _reviewedPlaces.value = _places.value.filter { it.status != PlaceStatus.PENDING}
    }

    fun findByID(id: String): Place? {
        return _places.value.find { it.id == id }
    }

    /*fun findByType(type: PlaceType): List<Place> {
        return _places.value.filter { it.type == type }
    }
    */

    fun findyName(name: String): List<Place> {
        return _approvedPlaces.value.filter { it.title.contains(name, ignoreCase = true) }
    }
    fun resetOperationResult(){
        _placeResult.value = null
    }
    fun loadPlaces() {
        /*_places.value = listOf(
                Place(
                    id = "1",
                    title = "Restaurante El paisa",
                    description = "El mejor restaurante paisa",
                    address = "Cra 12 # 12 - 12",
                    location = Location(4.53330, -75.67570), // REAL
                    images = listOf("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTM3Uv-2p7sm5kdLvUgjiDXwsdrtkYi7XTFfQ&s"),
                    phoneNumber = "3123132133, 3123132133",
                    type = PlaceType.RESTAURANT,
                    city = City.ARMENIA,
                    schedules = listOf(
                        Schedule(DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(20, 0)),
                        Schedule(DayOfWeek.THURSDAY, LocalTime.of(10, 0), LocalTime.of(20, 0)),
                        Schedule(DayOfWeek.FRIDAY, LocalTime.of(10, 0), LocalTime.of(20, 0))
                    ),
                    ownerId = "2"
                ),

                Place(
                    id = "2",
                    title = "Bar test",
                    description = "Un bar test",
                    address = "Calle 12 # 12 - 12",
                    location = Location(4.53420, -75.67290), // REAL
                    images = listOf("https://cdn0.uncomo.com/es/posts/6/8/4/como_gestionar_un_bar_22486_orig.jpg"),
                    phoneNumber = "3123123123",
                    type = PlaceType.BAR,
                    city = City.ARMENIA,
                    schedules = listOf(),
                    ownerId = "2"
                ),

                Place(
                    id = "3",
                    title = "Autoservicio Super A",
                    description = "Autoservicio Super Adiela",
                    address = "Manzana 29 Local 1 Etapa 3",
                    location = Location(4.53860, -75.67220), // REAL
                    images = listOf("https://rapiexpress.online/wp-content/uploads/2021/10/imagen-laureles-300x300.png"),
                    phoneNumber = "3123132133 , 3123132133",
                    type = PlaceType.SHOPPING,
                    city = City.ARMENIA,
                    schedules = listOf(),
                    ownerId = "3",
                    status = PlaceStatus.APPROVED
                )
            )

            updateFilteredLists()
            */
    }
}