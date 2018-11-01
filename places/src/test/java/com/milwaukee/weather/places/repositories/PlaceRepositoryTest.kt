package com.milwaukee.weather.places.repositories

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.milwaukee.weather.base.places.models.Place
import com.milwaukee.weather.basetest.readJsonFile
import com.milwaukee.weather.places.mappers.base.PlaceMapper
import com.milwaukee.weather.places.rest.AutocompleteService
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
class PlaceRepositoryTest {

    @Mock
    lateinit var mapper: PlaceMapper

    @Mock
    lateinit var service: AutocompleteService

    lateinit var query: String
    lateinit var types: String

    lateinit var repository: MilwaukeePlaceRepository

    @Before
    fun setupRepository() {
        MockitoAnnotations.initMocks(this)
        query = "Aruba"
        types = ""
        repository = MilwaukeePlaceRepository(mapper, service)
    }

    @Test
    fun `malformed service response - ask for places - get Error`() {
        whenever(service.getAutocompletePlaces(eq(query), eq(types))).thenReturn(Single.just(JsonObject()))
        repository.getPlacesFromQuery(query, types)
            .test()
            .assertError(Throwable::class.java)
    }

    @Test
    fun `get service response - ask for places - get expected value`() {
        val expectedValue = Place("ChIJcWGw3Ytzj1QR7Ui7HnTz6Dg", "Victoria", "BC, Canada")
        val mockedServiceResponse = JsonObject()
        mockedServiceResponse.add("predictions", JsonArray().apply {
            add(readJsonFile(RESULTS))
        })
        whenever(
            service.getAutocompletePlaces(
                eq(query),
                eq(types)
            )
        ).thenReturn(Single.just(mockedServiceResponse))
        whenever(mapper.getFromElement(any())).thenReturn(expectedValue)
        repository.getPlacesFromQuery(query, types)
            .test()
            .assertValueCount(1)
            .assertValueAt(0) { it.first() == expectedValue }
    }

    @Test
    fun `get service response with two values - ask for places -  remove one value and get one valid`() {
        val expectedValue = Place("ChIJcWGw3Ytzj1QR7Ui7HnTz6Dg", "Victoria", "BC, Canada")
        val validObject = readJsonFile(RESULTS)
        val inValidObject = JsonObject()
        val mockedServiceResponse = JsonObject()
        mockedServiceResponse.add("predictions", JsonArray().apply {
            add(validObject)
            add(inValidObject)
        })
        whenever(
            service.getAutocompletePlaces(
                eq(query),
                eq(types)
            )
        ).thenReturn(Single.just(mockedServiceResponse))
        whenever(mapper.getFromElement(eq(validObject))).thenReturn(expectedValue)
        whenever(mapper.getFromElement(eq(inValidObject))).thenThrow(Throwable())
        repository.getPlacesFromQuery(query, types)
            .test()
            .assertValueCount(1)
            .assertValueAt(0) { it.first() == expectedValue }
    }

    companion object {
        private const val RESULTS = "valid_autocomplete_object.json"
    }
}