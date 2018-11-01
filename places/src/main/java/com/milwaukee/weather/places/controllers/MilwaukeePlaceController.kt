package com.milwaukee.weather.places.controllers

import com.milwaukee.weather.base.model.Location
import com.milwaukee.weather.base.places.controllers.PlaceController
import com.milwaukee.weather.base.places.models.Place
import com.milwaukee.weather.base.places.models.SinglePlace
import com.milwaukee.weather.places.repositories.base.DetailRepository
import com.milwaukee.weather.places.repositories.base.GeocodeRepository
import com.milwaukee.weather.places.repositories.base.PlaceRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MilwaukeePlaceController @Inject constructor(
    private val placeRepository: PlaceRepository,
    private val detailRepository: DetailRepository,
    private val geocodeRepository: GeocodeRepository
) : PlaceController {

    override fun getPlacesAutocomplete(
        query: String,
        types: String?
    ): Single<List<Place>> {
        return placeRepository.getPlacesFromQuery(query, types)
            .observeOn(Schedulers.io())
            .onErrorReturn { emptyList() }
    }

    override fun getPlaceCoordinatesById(placeId: String): Single<Location> {
        return detailRepository.getDetailLocationFromPLaceId(placeId)
    }

    override fun getPlaceDataByCoordinates(location: Location): Single<SinglePlace> {
        return geocodeRepository.getPlaceFromCoordinates(location)
    }


}