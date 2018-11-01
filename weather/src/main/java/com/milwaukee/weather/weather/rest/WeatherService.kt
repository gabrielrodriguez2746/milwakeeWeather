package com.milwaukee.weather.weather.rest

import com.google.gson.JsonObject
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("weather")
    fun getWeatherByLocation(@Query("lat") latitude: Double,
                             @Query("lon") longitude: Double) : Single<JsonObject>

}