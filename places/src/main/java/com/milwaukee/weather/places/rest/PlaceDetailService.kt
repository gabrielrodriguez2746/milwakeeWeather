package com.milwaukee.weather.places.rest

import com.google.gson.JsonObject
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceDetailService {

    @GET("place/details/json")
    fun getDetailPlace(
        @Query("placeid") id: String,
        @Query("fields") fields: String
    ): Single<JsonObject>

}