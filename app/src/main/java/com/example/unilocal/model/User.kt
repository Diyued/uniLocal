package com.example.unilocal.model

data class User (
    var id: String = "",
    val name: String= "",
    val username: String = "",
    val role: Role= Role.USER,
    val email: String="",
    val city: City = City.ARMENIA,
    var password: String="",
    val favoritePlaces: List<String> = emptyList()
)
