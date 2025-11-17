package com.example.unilocal.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unilocal.model.Review
import com.example.unilocal.model.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ReviewsViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    // Este Flow contendrá las reseñas con la información del usuario ya incluida.
    private val _reviews = MutableStateFlow<List<Review>>(emptyList())
    val reviews = _reviews.asStateFlow()

    // Escucha las reseñas de un lugar específico en tiempo real.
    fun listenForReviews(placeId: String) {
        // Obtenemos solo las reseñas del lugar que nos interesa, ordenadas por fecha.
        db.collection("reviews")
            .whereEqualTo("placeID", placeId)
            .orderBy("date", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    e.printStackTrace()
                    return@addSnapshotListener
                }

                if (snapshot != null && !snapshot.isEmpty) {
                    viewModelScope.launch {
                        // Mapeamos cada reseña a nuestro nuevo modelo "ReviewUi"
                        val reviewList = snapshot.documents.mapNotNull { doc ->
                            val review = doc.toObject(Review::class.java)
                            review?.let {
                                // Por cada reseña, buscamos el nombre del autor
                                val author = getUser(it.userID)
                                Review(
                                    id = it.id,
                                    placeID = it.placeID,
                                    userID = it.userID,
                                    username = author?.name ?: "Anónimo", // Mostramos "Anónimo" si no se encuentra
                                    comment = it.comment,
                                    rating = it.rating,
                                    date = it.date
                                )
                            }
                        }
                        _reviews.value = reviewList
                    }
                } else {
                    _reviews.value = emptyList() // Si no hay reseñas, la lista estará vacía
                }
            }
    }

    // Obtiene un usuario específico de Firebase.
    private suspend fun getUser(userId: String): User? {
        return try {
            db.collection("users")
                .document(userId)
                .get()
                .await()
                .toObject(User::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // Crear nueva reseña en Firebase.
    fun create(review: Review) {
        val docRef = db.collection("reviews").document()

        val reviewToSave = review.copy(
            id = docRef.id,
            date = java.time.LocalDateTime.now().toString()
        )

        // Simplemente creamos la reseña. El listener se encargará de actualizar la UI.
        docRef.set(reviewToSave)
            .addOnFailureListener { e ->
                e.printStackTrace()
            }
    }
}