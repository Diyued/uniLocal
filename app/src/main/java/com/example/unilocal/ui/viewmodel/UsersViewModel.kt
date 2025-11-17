package com.example.unilocal.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unilocal.model.City
import com.example.unilocal.model.Role
import com.example.unilocal.model.User
import com.example.unilocal.utils.RequestResult
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class UsersViewModel: ViewModel() {
    private val _users = MutableStateFlow(emptyList<User>())
    val users: StateFlow<List<User>> = _users.asStateFlow()

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()


    private val _userResult = MutableStateFlow<RequestResult?>(null)
    val userResult: StateFlow<RequestResult?> = _userResult.asStateFlow()
    val db = Firebase.firestore

    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    init {
        auth.currentUser?.uid?.let {
            findById(it)
        }
    }
    fun create(user: User) {
        viewModelScope.launch {
            _userResult.value = RequestResult.Loading
            _userResult.value = runCatching { createFirebase(user) }
                .fold(
                    onSuccess = {
                        RequestResult.Success("Usuario creado Correctamente"
                        )
                    },
                    onFailure = {
                        RequestResult.Failure(it.message ?: "Error creando usuario"
                        )
                    }
                )
        }
    }

    private suspend fun createFirebase(user: User) { //funcion async
        val newUser = auth.createUserWithEmailAndPassword(user.email, user.password).await()
        val uid = newUser.user?.uid ?: throw Exception("Error al obtener el UID del usuario creado")

        val userCopy = user.copy(id = uid ?: "", password = "")

        db.collection("users")
            .document(uid)
            .set(userCopy)
            .await()

    }
    fun findById(id: String){
        viewModelScope.launch {
            _userResult.value = RequestResult.Loading
            _userResult.value = runCatching { findByIdFirebase(id) }
                .fold(
                    onSuccess = {
                        RequestResult.Success("Usuario Encontrado Correctamente"
                        )
                    },
                    onFailure = {
                        RequestResult.Failure(it.message ?: "Error obteniendo usuario"
                        )
                    }
                )
        }
    }
    private suspend fun findByIdFirebase(id: String) {
        val snapshot = db.collection( "users")
            .document( id)
            .get()
            .await()

        val user = snapshot.toObject(User::class.java)?.apply {
            this.id = snapshot.id
        }

        _currentUser.value = user
    }
    fun findByEmail(email: String): User? {
        return _users.value.find { it.email == email }
    }

    fun getUserById(id: String): User? {
        return _users.value.find { it.id == id }
    }
    fun changePassword(id: String, newPassword: String) {
        val user = getUserById(id)
        if (user != null) {
            user.password = newPassword
        }
    }

    fun update(user: User) {
        val currentUsers = _users.value.toMutableList()
        val index = currentUsers.indexOfFirst { it.id == user.id }

        if (index != -1) {
            currentUsers[index] = user
            _users.value = currentUsers
        }
    }
    fun resetOperationResult(){
        _userResult.value = null
    }


    fun login (email: String, password: String){
        viewModelScope.launch {
            _userResult.value = RequestResult.Loading
            _userResult.value = runCatching { loginFirebase(email, password) }
                .fold(
                    onSuccess = {
                        RequestResult.Success("Login Correcto"
                        )
                    },
                    onFailure = {
                        RequestResult.Failure("Datos de acceso incorrectos")
                    }
                )
        }
    }



    private suspend fun loginFirebase(email: String, password: String) {
        val responseUser = auth.signInWithEmailAndPassword(email, password).await()
        val uid = responseUser.user?.uid ?: throw Exception("Usuario no encontrado")
        findByIdFirebase(uid)
    }


    fun logout() {
        auth.signOut()
        _currentUser.value = null
    }

    fun sendPasswordReset(email: String) {
        viewModelScope.launch {
            _userResult.value = RequestResult.Loading

            try {
                auth.sendPasswordResetEmail(email).await()
                _userResult.value = RequestResult.Success("Correo enviado correctamente")
            } catch (e: Exception) {
                _userResult.value = RequestResult.Failure(e.message ?: "Error enviando correo")
            }
        }
    }


    fun addFavoritePlace(userId: String, placeId: String) {
        viewModelScope.launch {
            _userResult.value = RequestResult.Loading
            try {
                val userDocRef = db.collection("users").document(userId)
                // Añade de forma atómica el ID del lugar al array 'favoritePlaces'.
                // arrayUnion se asegura de que el ID se añada solo si no está ya presente.
                userDocRef.update("favoritePlaces", FieldValue.arrayUnion(placeId)).await()

                // Refresca el estado local del usuario desde Firestore para reflejar el cambio al instante
                findByIdFirebase(userId)

                _userResult.value = RequestResult.Success("Lugar guardado en favoritos")
            } catch (e: Exception) {
                Log.e("UsersViewModel", "Error adding favorite place", e)
                _userResult.value = RequestResult.Failure(e.message ?: "Error al guardar en favoritos")
            }
        }
    }

    fun removeFavoritePlace(userId: String, placeId: String) {
        viewModelScope.launch {
            _userResult.value = RequestResult.Loading
            try {
                val userDocRef = db.collection("users").document(userId)
                // Elimina de forma atómica el ID del lugar del array 'favoritePlaces'.
                userDocRef.update("favoritePlaces", FieldValue.arrayRemove(placeId)).await()

                // Refresca el estado local del usuario desde Firestore
                findByIdFirebase(userId)

                _userResult.value = RequestResult.Success("Lugar eliminado de favoritos")
            } catch (e: Exception) {
                Log.e("UsersViewModel", "Error removing favorite place", e)
                _userResult.value = RequestResult.Failure(e.message ?: "Error al eliminar de favoritos")
            }
        }
    }

}