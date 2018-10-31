package com.milwaukee.weather.landing.controllers

import android.Manifest
import com.milwaukee.weather.base.location.LocationProvider
import com.milwaukee.weather.base.model.Location
import com.milwaukee.weather.base.permissions.ActivityPermissionWrapper
import com.milwaukee.weather.base.permissions.UnknownException
import com.milwaukee.weather.landing.R
import io.reactivex.Single
import javax.inject.Inject

class MilwaukeeLandingController @Inject constructor(
    private val permissionsWrapper: ActivityPermissionWrapper,
    private val locationProvider: LocationProvider
) : LandingController {

    override fun getUserLocation(): Single<Location> {
        return requestLocationPermissions()
            .flatMap(::flatMapLocationBaseOnPermissions)
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