package com.milwaukee.weather.landing.di.controllers

import com.milwaukee.weather.landing.activities.LandingActivity
import io.reactivex.Maybe
import javax.inject.Inject

class MilwaukeePermissionsWrapper @Inject constructor(
    private val activity: LandingActivity
) : PermissionsWrapper {

    override fun getLocationPermissions(): Maybe<Boolean> {
        return Maybe.never()
    }
}