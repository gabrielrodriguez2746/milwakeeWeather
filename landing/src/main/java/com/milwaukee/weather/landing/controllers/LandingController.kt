package com.milwaukee.weather.landing.controllers

import com.milwaukee.weather.base.model.Location
import com.milwaukee.weather.base.weather.models.SingleWeather
import com.milwaukee.weather.base.places.controllers.PlaceController
import com.milwaukee.weather.base.weather.controllers.WeatherController
import io.reactivex.Single

/**
 * This class provides the current location asking for user permissions.
 */
interface LandingController : PlaceController,  WeatherController {

    /**
     * Returns a single with the current location after ask fo location permissions.
     * @return single with current location.
     * @throws [Throwable] if location can not be provide or permissions can not be granted
     */
    fun getUserLocation(): Single<Location>

}