package com.milwaukee.weather.di.modules

import com.milwaukee.weather.base.providers.ResourceProvider
import com.milwaukee.weather.weather.di.WeatherApiKey
import com.milwaukee.weather.weather.di.WeatherBaseUrl
import com.milwaukee.weather.R
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
object WeatherNetworkConfigurationModule {

    @Provides
    @JvmStatic
    @Reusable
    @WeatherApiKey
    fun provideWeatherApiKey(provider: ResourceProvider): String {
        return provider.getString(R.string.weather_api_key)
    }

    @Provides
    @JvmStatic
    @Reusable
    @WeatherBaseUrl
    fun provideWeatherApiBaseUrl(provider: ResourceProvider): String {
        return provider.getString(R.string.weather_base_url)
    }

}