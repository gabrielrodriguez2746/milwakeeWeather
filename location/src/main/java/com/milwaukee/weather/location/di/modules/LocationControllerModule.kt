package com.milwaukee.weather.location.di.modules

import com.milwaukee.weather.base.interfaces.LocationProvider
import com.milwaukee.weather.location.provider.MilwaukeeLocationController
import dagger.Binds
import dagger.Module

@Module
abstract class LocationControllerModule {

    @Binds
    abstract fun bindLOcationProvider(locationController: MilwaukeeLocationController): LocationProvider
}