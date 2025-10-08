package com.example.unilocal.model

data class Place (
    val id: String,
    val title: String,
    val description: String,
    val address: String,
    val location: String, //Location
    val images: String, //List<String>
    val phones: String, //List<String>
    val type: String, //PlaceType
    val schedule: String //List<Schedule>
)