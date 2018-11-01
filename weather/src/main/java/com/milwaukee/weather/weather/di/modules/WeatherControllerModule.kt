package com.milwaukee.weather.weather.di.modules

import com.milwaukee.weather.base.weather.controllers.WeatherController
import com.milwaukee.weather.weather.controllers.MilwaukeeWeatherController
import dagger.Binds
import dagger.Module

@Module(includes = [
    WeatherApiModule::class,
    WeatherModule::class
])
abstract class WeatherControllerModule {

    @Binds
    abstract fun bindWeatherController(controller: MilwaukeeWeatherController): WeatherController

}