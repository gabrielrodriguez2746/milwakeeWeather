package com.milwaukee.weather.places.repositories.base

import com.milwaukee.weather.base.model.Location
import com.milwaukee.weather.base.places.models.SinglePlace
import io.reactivex.Single

/**
 * This class provides the a place from coordinates.
 */
interface GeocodeRepository {

    /**
     * Get place from coordinates
     * @param location coordinates from place
     * @return [SinglePlace] mapped from search
     */
    fun getPlaceFromCoordinates(location: Location): Single<SinglePlace>
}