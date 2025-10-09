package com.example.unilocal.model

enum class PlaceType(
    override val displayName: String
): DisplayableEnum {
    RESTAURANT("Restaurante"),
    BAR("Bar"),
    HOTEL("Hotel"),
    PARK("Parque"),
    SHOPPING("Tienda"),
    OTHER("Otro")


}