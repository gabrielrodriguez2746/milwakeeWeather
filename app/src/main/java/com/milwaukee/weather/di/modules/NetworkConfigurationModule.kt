package com.milwaukee.weather.di.modules

import com.milwaukee.weather.base.di.MilwaukeeWeatherApiKey
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
object NetworkConfigurationModule {

    @Provides
    @JvmStatic
    @Reusable
    @MilwaukeeWeatherApiKey
    fun provideWetaherApiKey() = ""

    @Provides
    @JvmStatic
    @Reusable
    @MilwaukeeWeatherApiKey
    fun provideWetaherApiBaseUrl() = ""

}