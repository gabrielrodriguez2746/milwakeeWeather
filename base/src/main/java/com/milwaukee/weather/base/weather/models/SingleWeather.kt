package com.milwaukee.weather.base.weather.models

data class SingleWeather(
    val id: String,
    val description: String,
    val temperature: Double,
    val icon: String
)