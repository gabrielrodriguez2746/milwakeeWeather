package com.milwaukee.weather.weather.mappers

import com.google.gson.JsonObject
import com.milwaukee.weather.basetest.readJsonFile
import org.junit.Before
import org.junit.Test

class MilwaukeeWeatherMapperTest {

    private lateinit var mapper: MilwaukeeWeatherMapper

    @Before
    fun setupMapper() {
        mapper = MilwaukeeWeatherMapper()
    }

    @Test(expected = Throwable::class)
    fun `malformed data - map element - Throw Exception`() {
        mapper.getFromElement(JsonObject())
    }

    @Test
    fun `valid data - map element - valid data`() {
        val weather = mapper.getFromElement(readJsonFile(WEATHER_OBJECT))
        assert(weather.description == "nubes")
        assert(weather.icon == "04d")
        assert(weather.temperature == 268.727)
        assert(weather.id == "8145969")
    }

    companion object {
        private const val WEATHER_OBJECT = "valid_weather_object.json"
    }

}