package com.milwaukee.weather.places.mappers.base

import com.google.gson.JsonElement
import com.milwaukee.weather.base.mappers.BaseMapper
import com.milwaukee.weather.base.places.models.SinglePlace

interface GeocoderMapper : BaseMapper<JsonElement, SinglePlace>