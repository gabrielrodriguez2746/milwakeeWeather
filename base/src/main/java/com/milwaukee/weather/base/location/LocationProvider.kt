package com.milwaukee.weather.base.location

import com.milwaukee.weather.base.model.Location
import io.reactivex.Single

/**
 * This class provides the current location.
 */
interface LocationProvider {

    /**
     * Returns a single with the current location.
     * @return single with current location.
     * @throws [Throwable] if location can not be provide
     */
    fun getLocation(): Single<Location>


}