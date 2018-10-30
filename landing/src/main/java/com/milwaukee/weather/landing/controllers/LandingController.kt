package com.milwaukee.weather.landing.controllers

import com.milwaukee.weather.base.model.Location
import com.milwaukee.weather.base.permissions.ActivityPermissionWrapper
import io.reactivex.Single

interface LandingController : ActivityPermissionWrapper {

    fun getUserLocation(): Single<Location>

}