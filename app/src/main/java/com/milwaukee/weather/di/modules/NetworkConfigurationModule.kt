package com.milwaukee.weather.di.modules

import com.milwaukee.weather.base.di.MilwaukeeWeatherApiKey
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
object NetworkConfigurationModule {

    private const val API_KEY = "7c3280069fd3897156c39459f4b830d2"
    private const val BASE_URL = "api.openweathermap.org/data/2.5/"

    @Provides
    @JvmStatic
    @Reusable
    @MilwaukeeWeatherApiKey
    fun provideWeatherApiKey() = API_KEY

    @Provides
    @JvmStatic
    @Reusable
    @MilwaukeeWeatherApiKey
    fun provideWeatherApiBaseUrl() = BASE_URL

}