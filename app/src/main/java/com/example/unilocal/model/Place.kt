package com.example.unilocal.model

import java.time.LocalDateTime

data class Place(
    var id: String="",
    val title: String="",
    val description: String="",
    val address: String="",
    val city: City = City.ARMENIA,
    val location: Location=Location(),
    val images: List<String> = emptyList<String>(),
    val phoneNumber: String="",
    val type: PlaceType=PlaceType.RESTAURANT,
    val schedules: List<Schedule> = emptyList<Schedule>(),
    val ownerId: String="",
    val status: PlaceStatus = PlaceStatus.PENDING
){

    fun isOpen():Boolean{
        val now = LocalDateTime.now()

        if(schedules.isEmpty()){
            return false
        }

        val schedule = schedules.find { it.day == now.dayOfWeek }

        if(schedule == null){
            return false
        }

        return now.toLocalTime().isAfter(schedule.getOpenTime()) && now.toLocalTime().isBefore(schedule.getCloseTime())
    }

    fun hourClosed(): String{
        val now = LocalDateTime.now()

        if(schedules.isEmpty()){
            return ""
        }

        val schedule = schedules.find { it.day == now.dayOfWeek }

        if(schedule == null){
            return ""
        }

        return schedule.close.toString()
    }


}