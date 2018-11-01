package com.milwaukee.weather.weather.mappers

import com.google.gson.JsonElement
import com.milwaukee.weather.base.weather.models.SingleWeather
import com.milwaukee.weather.weather.mappers.base.WeatherMapper
import javax.inject.Inject

class MilwaukeeWeatherMapper @Inject constructor() : WeatherMapper {

    override fun getFromElement(element: JsonElement): SingleWeather {
        val weatherObject = element.asJsonObject
        val weatherDetailsObjects = weatherObject.getAsJsonArray("weather")
        val weatherIcon = weatherDetailsObjects.first().asJsonObject.get("icon").asString
        val weatherDescription = weatherDetailsObjects.joinToString(separator = ",")
        { it.asJsonObject.get("description").asString }
        val weatherId = weatherObject.get("id").asString
        val weatherTemperature = weatherObject.getAsJsonObject("main").get("temp").asDouble
        return SingleWeather(weatherId, weatherDescription, weatherTemperature, weatherIcon)
    }
}