package com.milwaukee.weather.places.mappers

import com.google.gson.JsonObject
import com.milwaukee.weather.basetest.readJsonFile
import org.junit.Before
import org.junit.Test

class MilwaukeePlaceMapperTest {

    private lateinit var mapper: MilwaukeePlaceMapper

    @Before
    fun setupMapper() {
        mapper = MilwaukeePlaceMapper()
    }

    @Test(expected = Throwable::class)
    fun `malformed data - map element - Throw Exception`() {
        mapper.getFromElement(JsonObject())
    }

    @Test
    fun `valid data - map element - valid data`() {
        val place = mapper.getFromElement(readJsonFile(AUTOCOMPLETE_OBJECT))
        assert(place.id == "ChIJcWGw3Ytzj1QR7Ui7HnTz6Dg")
        assert(place.text == "Victoria")
        assert(place.description == "BC, Canada")
    }

    companion object {
        private const val AUTOCOMPLETE_OBJECT = "valid_autocomplete_object.json"
    }

}