package com.milwaukee.weather.places.rest

import com.google.gson.JsonObject
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodeService {

    @GET("geocode/json")
    fun getGeocodePlace(@Query("latlng") location: String): Single<JsonObject>

}