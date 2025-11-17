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
        val newUser = auth.createUserWithEmailAndPassword(user.email, user.password).await()
        val uid = newUser.user?.uid ?: throw Exception("Error al obtener el UID del usuario creado")

        val userCopy = user.copy(id = uid ?: "", password = "")

        db.collection("users")
            .document(uid)
            .set(userCopy)
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