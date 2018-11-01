package com.milwaukee.weather.places.mappers

import com.milwaukee.weather.base.model.Location
import com.milwaukee.weather.places.mappers.base.LocationMapper
import java.text.DecimalFormat
import javax.inject.Inject

class MilwaukeeLocationMapper @Inject constructor(
    private val formatter: DecimalFormat
) : LocationMapper {

    override fun getFromElement(element: Location): String {
        val latitude = element.latitude.toString()
        val longitude = element.longitude.toString()
        return "$latitude,$longitude".takeIf { latitude.contains(".") }
            ?: "${formatter.parse(latitude)},${formatter.parse(longitude)}"
    }
}