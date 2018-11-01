package com.milwaukee.weather.places.repositories

import com.milwaukee.weather.base.places.models.Place
import com.milwaukee.weather.places.mappers.base.PlaceMapper
import com.milwaukee.weather.places.repositories.base.PlaceRepository
import com.milwaukee.weather.places.rest.AutocompleteService
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class MilwaukeePlaceRepository @Inject constructor(
    private val mapper: PlaceMapper,
    private val service: AutocompleteService
) : PlaceRepository {

    private val cacheData = HashMap<String, List<Place>>()

    override fun getPlacesFromQuery(
        query: String,
        types: String?
    ): Single<List<Place>> {
        val key = query.generateKey()
        return cacheData[key]?.let { data ->
            Single.just(data)
        } ?: service.getAutocompletePlaces(query, types)
            .map { it.getAsJsonArray("predictions") }
            .flatMapObservable {
                Observable.fromIterable(it)
                    .map(mapper::getFromElement)
                    .onErrorReturnItem(Place("-1"))
            }
            .filter { it.id != "-1" }
            .toList()
            .doOnSuccess { saveData(key, it) }
    }

    private fun saveData(key: String, places: List<Place>) {
        cacheData[key] = places
    }

    private fun String.generateKey(): String {
        return replace("\\s".toRegex(), "")
    }

}