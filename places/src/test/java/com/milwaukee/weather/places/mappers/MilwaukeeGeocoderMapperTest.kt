package com.milwaukee.weather.places.mappers

import com.google.gson.JsonObject
import com.milwaukee.weather.basetest.readJsonFile
import org.junit.Before
import org.junit.Test

class MilwaukeeGeocoderMapperTest {

    private lateinit var mapper: MilwaukeeGeocoderMapper

    @Before
    fun setupMapper() {
        mapper = MilwaukeeGeocoderMapper()
    }

    @Test(expected = Throwable::class)
    fun `malformed data - map element - Throw Exception`() {
        mapper.getFromElement(JsonObject())
    }

    @Test
    fun `valid data - map element - valid data`() {
        val place = mapper.getFromElement(readJsonFile(GEOCODER_OBJECT))
        assert(place.id == "ChIJT2x8Q2BZwokRpBu2jUzX3dE")
        assert(place.text == "279 Bedford Ave, Brooklyn, NY 11211, USA")
    }

    companion object {
        private const val GEOCODER_OBJECT = "valid_geocode_object.json"
    }

}