package com.milwaukee.weather.base.weather.controllers

import com.milwaukee.weather.base.model.Location
import com.milwaukee.weather.base.weather.models.SingleWeather
import io.reactivex.Single

/**
 * This class provide weather from location
 */
interface WeatherController {

    /**
     * Get weather features from service
     * @return Single with a weather
     */
    fun getWeatherByLocation(location: Location): Single<SingleWeather>

}