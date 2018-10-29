package com.milwaukee.weather.landing.di.controllers

import io.reactivex.Maybe

interface PermissionsWrapper {

    fun getLocationPermissions(): Maybe<Boolean>

}