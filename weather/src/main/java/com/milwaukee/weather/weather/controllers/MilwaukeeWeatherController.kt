package com.milwaukee.weather.weather.controllers

import com.milwaukee.weather.base.model.Location
import com.milwaukee.weather.base.weather.controllers.WeatherController
import com.milwaukee.weather.base.weather.models.SingleWeather
import com.milwaukee.weather.weather.respositories.base.WeatherRepository
import io.reactivex.Single
import javax.inject.Inject

class MilwaukeeWeatherController @Inject constructor(
    private val weatherRepository: WeatherRepository
) : WeatherController {

    override fun getWeatherByLocation(location: Location): Single<SingleWeather> {
        return weatherRepository.getSingleWeatherFromLocation(location)
    }
}