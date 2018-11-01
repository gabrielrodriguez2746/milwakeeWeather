package com.milwaukee.weather.places.mappers.base

import com.google.gson.JsonElement
import com.milwaukee.weather.base.mappers.BaseMapper
import com.milwaukee.weather.base.model.Location

interface DetailMapper : BaseMapper<JsonElement, Location>