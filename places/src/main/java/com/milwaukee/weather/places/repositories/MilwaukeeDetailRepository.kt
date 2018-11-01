package com.milwaukee.weather.places.repositories

import com.milwaukee.weather.base.model.Location
import com.milwaukee.weather.places.mappers.base.DetailMapper
import com.milwaukee.weather.places.repositories.base.DetailRepository
import com.milwaukee.weather.places.rest.PlaceDetailService
import io.reactivex.Single
import javax.inject.Inject

class MilwaukeeDetailRepository @Inject constructor(
    private val mapper: DetailMapper,
    private val detailService: PlaceDetailService
) : DetailRepository {

    private val cachePlaces = HashMap<String, Location>()

    override fun getDetailLocationFromPLaceId(id: String): Single<Location> {
        return if (cachePlaces.containsKey(id)) {
            Single.just(cachePlaces[id]!!)
        } else {
            detailService.getDetailPlace(id, GEOMETRY_REQUEST)
                .map { mapper.getFromElement(it.getAsJsonObject("result")) }
                .doOnSuccess { cachePlaces[id] = it }
        }
    }

    companion object {
        private const val GEOMETRY_REQUEST = "geometry"
    }

}