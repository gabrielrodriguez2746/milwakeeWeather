package com.milwaukee.weather.di.modules

import com.milwaukee.weather.base.di.modules.HttpClientModule
import com.milwaukee.weather.base.di.modules.HttpInterceptorModule
import dagger.Module

@Module(includes = [
    NetworkConfigurationModule::class,
    HttpClientModule::class,
    HttpInterceptorModule::class
])
class NetworkModule