package com.example.unilocal.model

data class User (
    val id: String,
    val name: String,
    val username: String,
    val role: Role,
    val email: String,
    val city: String,
    var password: String
)
