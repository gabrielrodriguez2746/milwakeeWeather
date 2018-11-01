package com.milwaukee.weather.landing.controllers

import android.Manifest
import com.milwaukee.weather.base.location.LocationController
import com.milwaukee.weather.base.model.Location
import com.milwaukee.weather.permissions.exceptions.UnknownException
import com.milwaukee.weather.base.places.controllers.PlaceController
import com.milwaukee.weather.landing.R
import com.milwaukee.weather.permissions.controllers.base.PermissionController
import io.reactivex.Single
import javax.inject.Inject

class MilwaukeeLandingController @Inject constructor(
    private val permissionsWrapper: PermissionController,
    private val locationProvider: LocationController,
    private val placeController: PlaceController
) : LandingController {

    override fun getUserLocation(): Single<Location> {
        return requestLocationPermissions()
            .flatMap(::flatMapLocationBaseOnPermissions)
    }

    override fun searchSuggestionByQuery(query: String): Single<Unit> {
        return Single.just(Unit)
    }

    override fun searchWeatherByLocation(location: Location): Single<Unit> {
        return Single.just(Unit)
    }

    private fun flatMapLocationBaseOnPermissions(hasPermissions: Boolean): Single<Location> {
        return if (hasPermissions) {
            locationProvider.getLocation()
        } else {
            throw UnknownException()
        }
    }

    private fun requestLocationPermissions(): Single<Boolean> {
        return permissionsWrapper.requestPermissions(
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), R.string.landing_permission_message,
            R.string.landing_permission_message, 1
        )
    }

}