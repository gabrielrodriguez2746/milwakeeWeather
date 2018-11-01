package com.milwaukee.weather.places.mappers

import com.google.gson.JsonElement
import com.milwaukee.weather.base.mappers.BaseMapper
import com.milwaukee.weather.base.model.Location
import com.milwaukee.weather.places.mappers.base.DetailMapper
import javax.inject.Inject

class MilwaukeeDetailMapper @Inject constructor() : DetailMapper {

    override fun getFromElement(element: JsonElement): Location {
        val detailObject = element.asJsonObject
        val location = detailObject.getAsJsonObject("geometry").getAsJsonObject("location")
        return Location(location.get("lat").asDouble, location.get("lng").asDouble)
    }

}