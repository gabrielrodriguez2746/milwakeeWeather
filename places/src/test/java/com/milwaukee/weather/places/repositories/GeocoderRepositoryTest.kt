package com.milwaukee.weather.places.repositories

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.milwaukee.weather.base.model.Location
import com.milwaukee.weather.base.places.models.SinglePlace
import com.milwaukee.weather.basetest.readJsonFile
import com.milwaukee.weather.places.mappers.base.GeocoderMapper
import com.milwaukee.weather.places.mappers.base.LocationMapper
import com.milwaukee.weather.places.rest.GeocodeService
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class GeocoderRepositoryTest {

    @Mock
    lateinit var mapper: GeocoderMapper

    @Mock
    lateinit var locationMapper: LocationMapper

    @Mock
    lateinit var service: GeocodeService

    lateinit var repository: MilwaukeeGeocoderRepository

    lateinit var mockedLocation: Location

    @Before
    fun setUpRepository() {
        MockitoAnnotations.initMocks(this)
        mockedLocation = Location(0.0, 0.0)
        repository = MilwaukeeGeocoderRepository(mapper, locationMapper, service)
    }

    @Test
    fun `malformed service response - ask for place - get Error`() {
        whenever(service.getGeocodePlace(anyString())).thenReturn(Single.just(JsonObject()))
        whenever(locationMapper.getFromElement(mockedLocation)).thenReturn("")
        repository.getPlaceFromCoordinates(mockedLocation)
            .test()
            .assertError(Throwable::class.java)
    }

    @Test
    fun `get service response - ask for place - get expected value`() {
        val expectedValue = SinglePlace("","")
        val mockedServiceResponse = JsonObject()
        mockedServiceResponse.add("results", JsonArray().apply {
            add(readJsonFile(RESULTS))
        })
        whenever(service.getGeocodePlace(anyString())).thenReturn(Single.just(mockedServiceResponse))
        whenever(locationMapper.getFromElement(mockedLocation)).thenReturn("")
        whenever(mapper.getFromElement(any())).thenReturn(expectedValue)
        repository.getPlaceFromCoordinates(mockedLocation)
            .test()
            .assertValue { it == expectedValue }
    }

    companion object {
        private const val RESULTS = "valid_geocode_object.json"
    }
}