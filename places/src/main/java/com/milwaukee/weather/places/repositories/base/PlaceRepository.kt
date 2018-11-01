package com.milwaukee.weather.places.repositories.base

import com.milwaukee.weather.base.places.models.Place
import io.reactivex.Single

/**
 * This class provides the a places from a query.
 */
interface PlaceRepository {

    /**
     * Get place from coordinates
     * @param query query searched
     * @param types for adjust the search
     * @return [List<Place>] mapped from search
     */
    fun getPlacesFromQuery(
        query: String,
        types: String?
    ): Single<List<Place>>

}