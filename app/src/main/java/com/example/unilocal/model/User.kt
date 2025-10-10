package com.example.unilocal.model

data class User (
    val id: String,
    val name: String,
    val username: String,
    val role: Role,
    val email: String,
    val city: String,
    val password: String,
    val favoritePlaces: List<String> = emptyList()
)
