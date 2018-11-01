package com.milwaukee.weather.places.repositories.base

import com.milwaukee.weather.base.model.Location
import io.reactivex.Single

/**
 * This class provides the a detail place from coordinates.
 */
interface DetailRepository {

    /**
     * Get place from coordinates
     * @param id place id
     * @return [Location] place coordinates
     */
    fun getDetailLocationFromPLaceId(id: String): Single<Location>

}