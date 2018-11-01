package com.milwaukee.weather.base.places.controllers

import com.milwaukee.weather.base.model.Location
import com.milwaukee.weather.base.places.models.Place
import com.milwaukee.weather.base.places.models.SinglePlace
import io.reactivex.Single

/**
 * This class provide places from query, id and coordinates
 */
interface PlaceController {

    /**
     * Get place list base on query searched
     * @return Single with a list of matched places
     */
    fun getPlacesAutocomplete(query: String,
                              types: String?): Single<List<Place>>

    /**
     * Get location coordinates base
     * @return Single with a list of matched places
     */
    fun getPlaceCoordinatesById(placeId: String): Single<Location>

    /**
     * Get place list base on query searched
     * @return Single with a list of matched places
     */
    fun getPlaceDataByCoordinates(location: Location): Single<SinglePlace>

}