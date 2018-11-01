package com.milwaukee.weather.landing.controllers

import com.milwaukee.weather.base.location.LocationController
import com.milwaukee.weather.base.model.Location
import com.milwaukee.weather.base.places.controllers.PlaceController
import com.milwaukee.weather.permissions.controllers.base.PermissionController
import com.milwaukee.weather.permissions.exceptions.CompleteDeniedPermissionsException
import com.milwaukee.weather.permissions.exceptions.UnknownException
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.anyArray
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class LandingControllerTest {

    @Mock
    private lateinit var permissionsController: PermissionController

    @Mock
    private lateinit var locationProvider: LocationController

    @Mock
    private lateinit var placeController: PlaceController

    private lateinit var controller: MilwaukeeLandingController

    @Before
    fun setupController() {
        MockitoAnnotations.initMocks(this)
        controller = MilwaukeeLandingController(permissionsController, locationProvider, placeController)
    }

    @Test(expected = CompleteDeniedPermissionsException::class)
    fun `Location permissions error - ask location - get Exception`() {
        val expectedException = CompleteDeniedPermissionsException()
        whenever(permissionsController.requestPermissions(anyArray(), any(), any(), any())).thenThrow(expectedException)
        controller.getUserLocation()
            .test()
            .assertError { it == expectedException }
    }

    @Test
    fun `Location permissions denied - ask location - get Exception`() {
        whenever(permissionsController.requestPermissions(anyArray(), any(), any(), any())).thenReturn(Single.just(false))
        controller.getUserLocation()
            .test()
            .assertError(UnknownException::class.java)
    }

    @Test(expected = Throwable::class)
    fun `Location permissions granted - ask location with no provider - get Exception`() {
        val expectedException = Throwable()
        whenever(permissionsController.requestPermissions(anyArray(), any(), any(), any())).thenReturn(Single.just(true))
        whenever(locationProvider.getLocation()).thenThrow(expectedException)
        controller.getUserLocation()
            .test()
            .assertError { it == expectedException }
    }

    @Test
    fun `Location permissions granted - ask location with provider - get Location`() {
        val expectedLocation = Location(0.0, 0.0)
        whenever(permissionsController.requestPermissions(anyArray(), any(), any(), any())).thenReturn(Single.just(true))
        whenever(locationProvider.getLocation()).thenReturn(Single.just(expectedLocation))
        controller.getUserLocation()
            .test()
            .assertNoErrors()
            .assertValue { it == expectedLocation }
    }

}