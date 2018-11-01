package com.milwaukee.weather.places.mappers

import com.google.gson.JsonElement
import com.milwaukee.weather.base.places.models.SinglePlace
import com.milwaukee.weather.places.mappers.base.GeocoderMapper
import javax.inject.Inject

class MilwaukeeGeocoderMapper @Inject constructor() : GeocoderMapper {

    override fun getFromElement(element: JsonElement): SinglePlace {
        val placeObject = element.asJsonObject
        return SinglePlace(placeObject["place_id"].asString, placeObject["formatted_address"].asString)
    }

}