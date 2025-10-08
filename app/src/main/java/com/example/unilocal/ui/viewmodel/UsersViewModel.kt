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
    fun login (email: String, password: String): User? {
        return _users.value.find { it.email == email && it.password == password }
    }
}