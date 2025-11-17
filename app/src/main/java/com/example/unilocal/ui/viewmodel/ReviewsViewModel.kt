package com.example.unilocal.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unilocal.model.Review
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ReviewsViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    private val _reviews = MutableStateFlow<List<Review>>(emptyList())
    val reviews: StateFlow<List<Review>> = _reviews.asStateFlow()


    init {
        loadReviews()
    }

    // Obtener reseñas filtradas por placeID
    fun getReviewsByPlace(placeID: String): List<Review> {
        return _reviews.value.filter { it.placeID == placeID }
    }

    // Crear nueva reseña en Firebase
    fun create(review: Review) {
        val docRef = db.collection("reviews").document()

        val reviewToSave = review.copy(
            id = docRef.id,
            date = java.time.LocalDateTime.now().toString()
        )

        docRef.set(reviewToSave)
            .addOnSuccessListener {
                _reviews.value = _reviews.value + reviewToSave
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
            }
    }

    // Cargar todas las reseñas desde Firebase
    fun loadReviews() {
        db.collection("reviews")
            .get()
            .addOnSuccessListener { result ->
                val list = result.documents.mapNotNull { it.toObject(Review::class.java) }
                _reviews.value = list
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
            }
    }
}
