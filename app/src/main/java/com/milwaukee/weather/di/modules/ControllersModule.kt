package com.milwaukee.weather.di.modules

import com.milwaukee.weather.location.di.modules.LocationControllerModule
import com.milwaukee.weather.places.di.modules.PlaceControllerModule
import com.milwaukee.weather.weather.di.modules.WeatherControllerModule
import dagger.Module

@Module(
    includes = [
        LocationControllerModule::class,
        PlaceControllerModule::class,
        WeatherControllerModule::class
    ]
)
class ControllersModule