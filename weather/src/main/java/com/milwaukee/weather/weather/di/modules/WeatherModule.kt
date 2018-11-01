package com.milwaukee.weather.weather.di.modules

import com.milwaukee.weather.weather.mappers.MilwaukeeWeatherMapper
import com.milwaukee.weather.weather.mappers.base.WeatherMapper
import com.milwaukee.weather.weather.respositories.MilwaukeeWeatherRepository
import com.milwaukee.weather.weather.respositories.base.WeatherRepository
import dagger.Binds
import dagger.Module

@Module
abstract class WeatherModule {

    @Binds
    abstract fun bindWeatherMapper(mapper: MilwaukeeWeatherMapper): WeatherMapper

    @Binds
    abstract fun bindWeatherRepository(repository: MilwaukeeWeatherRepository): WeatherRepository

}