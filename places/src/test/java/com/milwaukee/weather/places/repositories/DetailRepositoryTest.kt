package com.milwaukee.weather.places.repositories

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.milwaukee.weather.base.mappers.BaseMapper
import com.milwaukee.weather.base.model.Location
import com.milwaukee.weather.basetest.readJsonFile
import com.milwaukee.weather.places.rest.PlaceDetailService
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.eq
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
class DetailRepositoryTest {

    @Mock
    lateinit var mapper: BaseMapper<JsonElement, Location>

    @Mock
    lateinit var detailService: PlaceDetailService

    lateinit var repository: MilwaukeeDetailRepository

    lateinit var id : String

    @Before
    fun setUpRepository() {
        MockitoAnnotations.initMocks(this)
        id = ""
        repository = MilwaukeeDetailRepository(mapper, detailService)
    }

    @Test
    fun `malformed service response - ask for detail - get Error`() {
        whenever(detailService.getDetailPlace(eq(id), anyString())).thenReturn(Single.just(JsonObject()))
        repository.getDetailLocationFromPLaceId(id)
            .test()
            .assertError(Throwable::class.java)
    }

    @Test
    fun `get service response - ask for detail - get expected value`() {
        val expectedValue = Location(0.0,0.0)
        val mockedServiceResponse = JsonObject().apply {
            add("result", readJsonFile(RESULTS))
        }
        whenever(detailService.getDetailPlace(eq(id), anyString())).thenReturn(Single.just(mockedServiceResponse))
        whenever(mapper.getFromElement(any())).thenReturn(expectedValue)
        repository.getDetailLocationFromPLaceId(id)
            .test()
            .assertValue { it == expectedValue }
    }

    companion object {
        private const val RESULTS = "valid_details_object.json"
    }
}