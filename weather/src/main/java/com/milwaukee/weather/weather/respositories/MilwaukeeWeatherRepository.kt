package com.milwaukee.weather.weather.respositories

import com.milwaukee.weather.base.model.Location
import com.milwaukee.weather.base.weather.models.SingleWeather
import com.milwaukee.weather.weather.mappers.base.WeatherMapper
import com.milwaukee.weather.weather.respositories.base.WeatherRepository
import com.milwaukee.weather.weather.rest.WeatherService
import io.reactivex.Single
import javax.inject.Inject

class MilwaukeeWeatherRepository @Inject constructor(
    private val mapper: WeatherMapper,
    private val service: WeatherService
) : WeatherRepository {

    private val cacheData = HashMap<Int, SingleWeather>() // TODO This should have time as an special consideration

    override fun getSingleWeatherFromLocation(location: Location): Single<SingleWeather> {
        val key = location.hashCode()
        return cacheData[key]?.let { data ->
            Single.just(data)
        } ?: service.getWeatherByLocation(location.latitude, location.longitude)
            .map(mapper::getFromElement)
            .doOnSuccess { cacheData[key] = it }
    }
}