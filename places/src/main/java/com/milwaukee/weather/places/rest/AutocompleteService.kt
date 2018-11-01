package com.milwaukee.weather.places.rest

import com.google.gson.JsonObject
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface AutocompleteService {

    @GET("place/autocomplete/json")
    fun getAutocompletePlaces(
        @Query("input") input: String,
        @Query("types") types: String?
    ): Single<JsonObject>

}