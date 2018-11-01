package com.milwaukee.weather.weather.di.modules

import com.milwaukee.weather.weather.di.WeatherService
import dagger.Module
import dagger.Provides
import dagger.Reusable
import retrofit2.Retrofit
import com.milwaukee.weather.weather.rest.WeatherService as RestWeatherService

@Module
object WeatherApiModule {

    @Provides
    @Reusable
    @JvmStatic
    fun provideWeatherService(@WeatherService retrofit: Retrofit): RestWeatherService {
        return retrofit.create(RestWeatherService::class.java)
    }

}