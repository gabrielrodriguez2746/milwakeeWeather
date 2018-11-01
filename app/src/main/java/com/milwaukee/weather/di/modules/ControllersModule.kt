package com.milwaukee.weather.di.modules

import com.milwaukee.weather.location.di.modules.LocationControllerModule
import com.milwaukee.weather.places.di.modules.PlaceControllerModule
import dagger.Module

@Module(
    includes = [
        LocationControllerModule::class,
        PlaceControllerModule::class
    ]
)
class ControllersModule