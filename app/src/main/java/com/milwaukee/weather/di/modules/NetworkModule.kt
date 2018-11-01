package com.milwaukee.weather.di.modules

import com.milwaukee.weather.base.di.modules.BaseInterceptorModule
import com.milwaukee.weather.places.di.modules.PlaceClientModule
import com.milwaukee.weather.places.di.modules.PlaceInterceptorModule
import com.milwaukee.weather.weather.di.modules.WeatherClientModule
import com.milwaukee.weather.weather.di.modules.WeatherInterceptorModule
import dagger.Module

@Module(
    includes = [
        BaseInterceptorModule::class,
        WeatherNetworkConfigurationModule::class,
        WeatherClientModule::class,
        WeatherInterceptorModule::class,
        PlaceNetworkConfigurationModule::class,
        PlaceClientModule::class,
        PlaceInterceptorModule::class
    ]
)
class NetworkModule