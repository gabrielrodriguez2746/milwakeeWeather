package com.milwaukee.weather.landing

import com.milwaukee.weather.base.location.LocationProvider
import com.milwaukee.weather.base.model.Location
import com.milwaukee.weather.base.permissions.ActivityPermissionWrapper
import com.milwaukee.weather.base.permissions.CompleteDeniedPermissionsException
import com.milwaukee.weather.base.permissions.UnknownException
import com.milwaukee.weather.landing.controllers.MilwaukeeLandingController
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
    private lateinit var permissionsWrapper: ActivityPermissionWrapper

    @Mock
    private lateinit var locationProvider: LocationProvider

    private lateinit var controller: MilwaukeeLandingController

    @Before
    fun setupController() {
        MockitoAnnotations.initMocks(this)
        controller = MilwaukeeLandingController(permissionsWrapper, locationProvider)
    }

    @Test(expected = CompleteDeniedPermissionsException::class)
    fun `Location permissions error - ask location - get Exception`() {
        val expectedException = CompleteDeniedPermissionsException()
        whenever(permissionsWrapper.requestPermissions(anyArray(), any(), any(), any())).thenThrow(expectedException)
        controller.getUserLocation()
            .test()
            .assertError { it == expectedException }
    }

    @Test
    fun `Location permissions denied - ask location - get Exception`() {
        whenever(permissionsWrapper.requestPermissions(anyArray(), any(), any(), any())).thenReturn(Single.just(false))
        controller.getUserLocation()
            .test()
            .assertError(UnknownException::class.java)
    }

    @Test(expected = Throwable::class)
    fun `Location permissions granted - ask location with no provider - get Exception`() {
        val expectedException = Throwable()
        whenever(permissionsWrapper.requestPermissions(anyArray(), any(), any(), any())).thenReturn(Single.just(true))
        whenever(locationProvider.getLocation()).thenThrow(expectedException)
        controller.getUserLocation()
            .test()
            .assertError { it == expectedException }
    }

    @Test
    fun `Location permissions granted - ask location with provider - get Location`() {
        val expectedLocation = Location(0.0, 0.0)
        whenever(permissionsWrapper.requestPermissions(anyArray(), any(), any(), any())).thenReturn(Single.just(true))
        whenever(locationProvider.getLocation()).thenReturn(Single.just(expectedLocation))
        controller.getUserLocation()
            .test()
            .assertNoErrors()
            .assertValue { it == expectedLocation }
    }

}