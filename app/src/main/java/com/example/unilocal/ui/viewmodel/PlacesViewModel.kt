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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.time.DayOfWeek
import java.time.LocalTime

class PlacesViewModel : ViewModel() {

    private val db = Firebase.firestore

    // Todas las listas
    private val _places = MutableStateFlow(emptyList<Place>())
    val places = _places.asStateFlow()

    private val _approvedPlaces = MutableStateFlow(emptyList<Place>())
    val approvedPlaces = _approvedPlaces.asStateFlow()

    private val _pendingPlaces = MutableStateFlow(emptyList<Place>())
    val pendingPlaces = _pendingPlaces.asStateFlow()

    private val _rejectedPlaces = MutableStateFlow(emptyList<Place>())
    val rejectedPlaces = _rejectedPlaces.asStateFlow()

    private val _myPlaces = MutableStateFlow(emptyList<Place>())
    val myPlaces = _myPlaces.asStateFlow()

    private val _currentPlace = MutableStateFlow<Place?>(null)
    val currentPlace = _currentPlace.asStateFlow()

    private val _reviewedPlaces = MutableStateFlow<List<String>>(emptyList())
    val reviewedPlaces: StateFlow<List<String>> = _reviewedPlaces.asStateFlow()

    private val _placeResult = MutableStateFlow<RequestResult?>(null)
    val placeResult = _placeResult.asStateFlow()

    private val _myApprovedPlaces = MutableStateFlow(emptyList<Place>())
    val myApprovedPlaces = _myApprovedPlaces.asStateFlow()

    init {
        listenAllPlaces()
    }

    // 🔥 ESCUCHA TODOS LOS LUGARES EN TIEMPO REAL
    private fun listenAllPlaces() {
        db.collection("places")
            .addSnapshotListener { snapshot, error ->
                if (snapshot != null) {
                    val list = snapshot.documents.mapNotNull { doc ->
                        doc.toObject(Place::class.java)?.copy(id = doc.id)
                    }
                    _places.value = list
                    updateFilteredLists()
                }
            }
    }

    // 🔥 LISTAS SEPARADAS SEGÚN ESTADO
    private fun updateFilteredLists() {
        _approvedPlaces.value = _places.value.filter { it.status == PlaceStatus.APPROVED}
        _pendingPlaces.value = _places.value.filter { it.status == PlaceStatus.PENDING}
        _rejectedPlaces.value = _places.value.filter { it.status == PlaceStatus.REJECTED}
    }

    // 🔥 SOLO MIS LUGARES COMO DUEÑO
    fun loadMyPlaces(ownerId: String) {
        val userPlaces = _places.value.filter { it.ownerId == ownerId }
        _myPlaces.value = userPlaces
        _myApprovedPlaces.value = userPlaces.filter { it.status == PlaceStatus.APPROVED }
    }

    // 🔥 CREAR LUGAR EN FIREBASE
    fun create(place: Place) {
        viewModelScope.launch {
            _placeResult.value = RequestResult.Loading

            val placeClean = place.copy(
                id = "",
                city = place.city,
                type = place.type,
                status = PlaceStatus.PENDING
            )

            runCatching {
                db.collection("places")
                    .add(placeClean)
                    .await()
            }.fold(
                onSuccess = {
                    _placeResult.value = RequestResult.Success("Lugar creado correctamente")
                },
                onFailure = {
                    _placeResult.value = RequestResult.Failure(it.message ?: "Error creando lugar")
                }
            )
        }
    }

    // 🔥 APROBAR UN LUGAR (ADMIN)
    fun approvePlace(placeId: String) {
        updateStatus(placeId, PlaceStatus.APPROVED)
    }

    // 🔥 RECHAZAR UN LUGAR (ADMIN)
    fun rejectPlace(placeId: String) {
        updateStatus(placeId, PlaceStatus.REJECTED)
    }
    val allReviewedPlaces = combine(approvedPlaces, rejectedPlaces) { approved, rejected ->
        approved + rejected
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )


    private fun updateStatus(placeId: String, status: PlaceStatus) {
        viewModelScope.launch {
            db.collection("places")
                .document(placeId)
                .update("status", status.name)
                .await()
        }
    }

    fun findById(id: String): Place? {
        return _places.value.find { it.id == id }
    }

    fun findByName(name: String): List<Place> {
        return _approvedPlaces.value.filter {
            it.title.contains(name, ignoreCase = true)
        }
    }

    fun resetOperationResult() {
        _placeResult.value = null
    }
}
