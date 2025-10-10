package com.example.unilocal.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.unilocal.model.Role
import com.example.unilocal.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class UsersViewModel: ViewModel() {
    private val _users = MutableStateFlow(emptyList<User>())
    val users: StateFlow<List<User>> = _users.asStateFlow()

    init {
        loadUsers()
    }
    fun loadUsers() {
        _users.value = listOf(
            User(
                id ="1",
                name = "Admin",
                username = "admin",
                role = Role.ADMIN,
                city = "Armenia",
                email = "admin@email.com",
                password = "123456"
            ),
            User(
                id ="2",
                name = "Carlos",
                username = "carlos",
                role = Role.USER,
                city = "Armenia",
                email = "carlos@email.com",
                password = "123456"
            ),
            User(
                id ="3",
                name = "Diego",
                username = "Diego",
                role = Role.USER,
                city = "Armenia",
                email = "diego@email.com",
                password = "123456"
            )
        )
    }
    fun create(user: User) {
        _users.value = _users.value + user
    }
    fun findbyID(id: String): User? {
        return _users.value.find { it.id == id }
    }
    fun findByEmail(email: String): User? {
        return _users.value.find { it.email == email }
    }
    fun getUserById(id: String): User? {
        return _users.value.find { it.id == id }
    }

    fun update(user: User) {
        val currentUsers = _users.value.toMutableList()
        val index = currentUsers.indexOfFirst { it.id == user.id }

        if (index != -1) {
            currentUsers[index] = user
            _users.value = currentUsers
        }
    }
    fun login (email: String, password: String): User? {
        return _users.value.find { it.email == email && it.password == password }
    }
    fun addFavoritePlace(userId: String, placeId: String) {
        val currentUsers = _users.value
        val userToUpdate = currentUsers.find { it.id == userId }

        println("🔍 addFavoritePlace - userId: $userId, placeId: $placeId")
        println("🔍 Usuario encontrado: ${userToUpdate?.email}")
        println("🔍 Favoritos actuales: ${userToUpdate?.favoritePlaces}")

        if (userToUpdate != null) {
            val updatedFavorites = (userToUpdate.favoritePlaces ?: emptyList()).toMutableList()
            if (!updatedFavorites.contains(placeId)) {
                updatedFavorites.add(placeId)
            }

            val updatedUser = userToUpdate.copy(favoritePlaces = updatedFavorites)

            println("🔍 Favoritos después: ${updatedUser.favoritePlaces}")

            _users.value = currentUsers.map {
                if (it.id == userId) updatedUser else it
            }

            println("✅ Usuario actualizado en StateFlow")
        } else {
            println("❌ Usuario NO encontrado con ID: $userId")
        }
    }

    fun removeFavoritePlace(userId: String, placeId: String) {
        val currentUsers = _users.value  // 👈 Guardamos la lista actual
        val userToUpdate = currentUsers.find { it.id == userId }

        if (userToUpdate != null) {
            val updatedFavorites = (userToUpdate.favoritePlaces ?: emptyList()).toMutableList()
            updatedFavorites.remove(placeId)

            val updatedUser = userToUpdate.copy(favoritePlaces = updatedFavorites)

            // 👈 Actualizamos la lista completa de forma inmutable
            _users.value = currentUsers.map {
                if (it.id == userId) updatedUser else it
            }
        }
    }

}