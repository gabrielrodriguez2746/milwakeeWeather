package com.milwaukee.weather.places.repositories

import com.milwaukee.weather.base.model.Location
import com.milwaukee.weather.base.places.models.SinglePlace
import com.milwaukee.weather.places.mappers.base.GeocoderMapper
import com.milwaukee.weather.places.mappers.base.LocationMapper
import com.milwaukee.weather.places.repositories.base.GeocodeRepository
import com.milwaukee.weather.places.rest.GeocodeService
import io.reactivex.Single
import javax.inject.Inject

class MilwaukeeGeocoderRepository @Inject constructor(
    private val mapper: GeocoderMapper,
    private val locationMapper: LocationMapper,
    private val service: GeocodeService
) : GeocodeRepository {

    private val cacheData = HashMap<String, SinglePlace>()

    override fun getPlaceFromCoordinates(location: Location): Single<SinglePlace> {
        val key = locationMapper.getFromElement(location)
        return cacheData[key]?.let {
            Single.just(it)
        } ?: service.getGeocodePlace(key)
            .map { mapper.getFromElement(it.getAsJsonArray("results").first()) }
            .doOnSuccess { cacheData[key] = it }
    }
}