package com.milwaukee.weather.places.mappers

import com.google.gson.JsonElement
import com.milwaukee.weather.base.mappers.BaseMapper
import com.milwaukee.weather.base.places.models.Place
import com.milwaukee.weather.places.mappers.base.PlaceMapper
import javax.inject.Inject

class MilwaukeePlaceMapper @Inject constructor() : PlaceMapper {

    override fun getFromElement(element: JsonElement): Place {
        val placeObject = element.asJsonObject
        val structuredFormat = placeObject.getAsJsonObject("structured_formatting")
        return Place(placeObject["place_id"].asString,
            structuredFormat["main_text"].asString,
            structuredFormat["secondary_text"].asString)
    }
}