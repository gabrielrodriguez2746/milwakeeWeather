package com.milwaukee.weather.places.mappers.base

import com.google.gson.JsonElement
import com.milwaukee.weather.base.mappers.BaseMapper
import com.milwaukee.weather.base.places.models.Place

interface PlaceMapper : BaseMapper<JsonElement, Place>