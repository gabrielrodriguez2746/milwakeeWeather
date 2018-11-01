package com.milwaukee.weather.weather.mappers.base

import com.google.gson.JsonElement
import com.milwaukee.weather.base.mappers.BaseMapper
import com.milwaukee.weather.base.weather.models.SingleWeather

interface WeatherMapper : BaseMapper<JsonElement, SingleWeather>