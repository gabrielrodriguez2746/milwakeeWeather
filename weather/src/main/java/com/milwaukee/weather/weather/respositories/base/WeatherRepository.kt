package com.milwaukee.weather.weather.respositories.base

import com.milwaukee.weather.base.model.Location
import com.milwaukee.weather.base.weather.models.SingleWeather
import com.milwaukee.weather.weather.mappers.base.WeatherMapper
import io.reactivex.Single

/**
 * This class provides the weather details from coordinates.
 */
interface WeatherRepository {

    /**
     * Get weather details from coordinates
     * @param location location coordinates
     * @return [SingleWeather] weather details
     */
    fun getSingleWeatherFromLocation(location: Location): Single<SingleWeather>

}