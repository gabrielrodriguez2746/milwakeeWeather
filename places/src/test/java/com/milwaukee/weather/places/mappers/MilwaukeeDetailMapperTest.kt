package com.milwaukee.weather.places.mappers

import com.google.gson.JsonObject
import com.milwaukee.weather.basetest.readJsonFile
import org.junit.Before
import org.junit.Test

class MilwaukeeDetailMapperTest {

    private lateinit var mapper: MilwaukeeDetailMapper

    @Before
    fun setupFormatter() {
        mapper = MilwaukeeDetailMapper()
    }

    @Test(expected = Throwable::class)
    fun `malformed data - map element - Throw Exception`() {
        mapper.getFromElement(JsonObject())
    }

    @Test
    fun `valid data - map element - valid data`() {
        val place = mapper.getFromElement(readJsonFile(DETAIL_OBJECT))
        assert(place.latitude == 4.602031600000001)
        assert(place.longitude == -74.075887)
    }

    companion object {
        private const val DETAIL_OBJECT = "valid_details_object.json"
    }

}