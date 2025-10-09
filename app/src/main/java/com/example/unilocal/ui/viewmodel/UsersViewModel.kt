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
}