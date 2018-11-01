package com.milwaukee.weather.di.modules

import com.milwaukee.weather.base.di.modules.HttpClientModule
import com.milwaukee.weather.base.di.modules.HttpInterceptorModule
import com.milwaukee.weather.places.di.modules.PlaceClientModule
import com.milwaukee.weather.places.di.modules.PlaceInterceptorModule
import dagger.Module

@Module(includes = [
    AppNetworkConfigurationModule::class,
    HttpClientModule::class,
    HttpInterceptorModule::class,
    PlaceNetworkConfigurationModule::class,
    PlaceClientModule::class,
    PlaceInterceptorModule::class
])
class NetworkModule