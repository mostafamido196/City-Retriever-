package com.samy.klivvrandroidchallenge.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class City(
    val country: String,
    val name: String,
    val _id: Long,
    val coord: Coord,

){
    data class Coord(
        val lat: Double,
        val lon: Double
    )
}