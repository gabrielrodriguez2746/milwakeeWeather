package com.milwaukee.weather.landing.controllers

import com.milwaukee.weather.base.model.Location
import io.reactivex.Single

/**
 * This class provides the current location asking for user permissions.
 */
interface LandingController {

    /**
     * Returns a single with the current location after ask fo location permissions.
     * @return single with current location.
     * @throws [Throwable] if location can not be provide or permissions can not be granted
     */
    fun getUserLocation(): Single<Location>

    fun searchSuggestionByQuery(query: String) : Single<Unit>

    fun searchWeatherByLocation(location: Location) : Single<Unit>

}