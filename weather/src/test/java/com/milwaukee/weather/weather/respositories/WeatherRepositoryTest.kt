package com.milwaukee.weather.weather.respositories

import com.google.gson.JsonObject
import com.milwaukee.weather.base.model.Location
import com.milwaukee.weather.base.weather.models.SingleWeather
import com.milwaukee.weather.basetest.readJsonFile
import com.milwaukee.weather.weather.mappers.base.WeatherMapper
import com.milwaukee.weather.weather.rest.WeatherService
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class WeatherRepositoryTest {

    @Mock
    lateinit var mapper: WeatherMapper

    @Mock
    lateinit var service: WeatherService

    val latitude = 40.714224
    val longitude = -73.961452

    lateinit var location: Location

    lateinit var repository: MilwaukeeWeatherRepository

    @Before
    fun setUpRepository() {
        MockitoAnnotations.initMocks(this)
        location = Location(latitude, longitude)
        repository = MilwaukeeWeatherRepository(mapper, service)
    }

    @Test
    fun `malformed service response - ask for weather - get Error`() {
        whenever(service.getWeatherByLocation(eq(latitude), eq(longitude))).thenReturn(Single.just(JsonObject()))
        repository.getSingleWeatherFromLocation(location)
            .test()
            .assertError(Throwable::class.java)
    }

    @Test
    fun `get service response - ask for weather - get expected value`() {
        val expectedValue = SingleWeather("8145969", "nubes", 268.727, "04d")
        whenever(
            service.getWeatherByLocation(
                eq(latitude),
                eq(longitude)
            )
        ).thenReturn(Single.just(readJsonFile(RESULTS)))
        whenever(mapper.getFromElement(any())).thenReturn(expectedValue)
        repository.getSingleWeatherFromLocation(location)
            .test()
            .assertValue { it == expectedValue }
    }

    companion object {
        private const val RESULTS = "valid_weather_object.json"
    }
}