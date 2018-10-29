package com.milwaukee.weather.base.interfaces

import com.milwaukee.weather.base.model.Location
import io.reactivex.Single

/**
 * This class provides the current location.
 */
interface LocationProvider {

    /**
     * Returns a single with the current location.
     * @return single with current location.
     */
    fun getLocation(): Single<Location>


}