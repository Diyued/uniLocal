package com.example.unilocal.model

data class Review(
    val id: String = "",
    val userID: String = "",
    val username: String = "",
    val placeID: String = "",
    val rating: Int = 0,
    val comment: String = "",
    val date: String = ""
)
