package com.example.unilocal.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unilocal.model.City
import com.example.unilocal.model.Role
import com.example.unilocal.model.User
import com.example.unilocal.utils.RequestResult
import com.google.firebase.Firebase
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
    init {
        loadUsers()
        viewModelScope.launch {
            currentUser.collect { user ->
                Log.d("UsersViewModel", "currentUser cambió: $user")
            }
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
        db.collection("users")
            .add(user)
            .await()
    }
    fun findbyID(id: String){
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

    fun loadUsers() {
        User(
            id ="1",
            name = "Admin",
            username = "admin",
            role = Role.ADMIN,
            city = City.ARMENIA,
            email = "admin@email.com",
            password = "123456"
        )
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
                        RequestResult.Failure(it.message ?: "Error en el login"
                        )
                    }
                )
        }
    }

    private suspend fun loginFirebase(email: String, password: String) {
        val snapshot = db.collection( "users")
            .whereEqualTo("email",  email)
            .whereEqualTo("password", password)
            .get()
            .await()

        if (snapshot.isEmpty) {
            throw Exception("Usuario no encontrado")
        }else {

            snapshot.documents.mapNotNull {
                var user = it.toObject(User::class.java)?.apply {
                    this.id = it.id
                }
                _currentUser.value = user
            }
        }
    }


    fun logout() {
        _currentUser.value = null
    }

    fun addFavoritePlace(userId: String, placeId: String) {
        val currentUsers = _users.value
        val userToUpdate = currentUsers.find { it.id == userId }

        println("🔍 addFavoritePlace - userId: $userId, placeId: $placeId")
        println("🔍 Usuario encontrado: ${userToUpdate?.email}")
        println("🔍 Favoritos actuales: ${userToUpdate?.favoritePlaces}")

        if (userToUpdate != null) {
            val updatedFavorites = (userToUpdate.favoritePlaces ?: emptyList()) + placeId
            val updatedUser = userToUpdate.copy(favoritePlaces = updatedFavorites)

            _users.value = currentUsers.map {
                if (it.id == userId) updatedUser else it
            }

            if (_currentUser.value?.id == userId) {
                _currentUser.value = updatedUser
            }


            println("🔍 Favoritos después: ${updatedUser.favoritePlaces}")



            println("✅ Usuario actualizado en StateFlow")
        } else {
            println("❌ Usuario NO encontrado con ID: $userId")
        }
    }

    fun removeFavoritePlace(userId: String, placeId: String) {
        val currentUsers = _users.value  // 👈 Guardamos la lista actual
        val userToUpdate = currentUsers.find { it.id == userId }

        if (userToUpdate != null) {
            val updatedFavorites = (userToUpdate.favoritePlaces ?: emptyList()).filter { it != placeId }
            val updatedUser = userToUpdate.copy(favoritePlaces = updatedFavorites)

            _users.value = currentUsers.map {
                if (it.id == userId) updatedUser else it
            }

            if (_currentUser.value?.id == userId) {
                _currentUser.value = updatedUser
            }
        }


    }

}