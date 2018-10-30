package com.milwaukee.weather.landing.controllers

import android.Manifest
import com.milwaukee.weather.base.location.LocationProvider
import com.milwaukee.weather.base.model.Location
import com.milwaukee.weather.base.permissions.ActivityPermissionWrapper
import com.milwaukee.weather.landing.R
import io.reactivex.Single
import javax.inject.Inject

class MilwaukeeLandingController @Inject constructor(
    permissionsWrapper: ActivityPermissionWrapper,
    private val locationProvider: LocationProvider
) : LandingController, ActivityPermissionWrapper by permissionsWrapper {

    override fun getUserLocation(): Single<Location> {
        return requestPermissions(
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), R.string.landing_permission_message,
            R.string.landing_permission_message, 1
        ).flatMap { if (it) locationProvider.getLocation() else throw Throwable() }
    }

}