package com.milwaukee.weather.places.di.modules

import com.milwaukee.weather.base.places.controllers.PlaceController
import com.milwaukee.weather.places.controllers.MilwaukeePlaceController
import dagger.Binds
import dagger.Module

@Module(
    includes = [
        PlacesApiModule::class,
        PlaceModule::class
    ]
)
abstract class PlaceControllerModule {

    @Binds
    abstract fun bindMapsController(controller: MilwaukeePlaceController): PlaceController

}