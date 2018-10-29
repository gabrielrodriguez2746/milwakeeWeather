package com.milwaukee.weather.di.modules

import com.milwaukee.weather.location.di.modules.LocationControllerModule
import dagger.Module

@Module(
    includes = [
        LocationControllerModule::class
    ]
)
class ControllersModule {
}