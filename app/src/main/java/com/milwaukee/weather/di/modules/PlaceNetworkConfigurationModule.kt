package com.milwaukee.weather.di.modules

import com.milwaukee.weather.R
import com.milwaukee.weather.base.providers.ResourceProvider
import com.milwaukee.weather.places.di.PlaceApiKey
import com.milwaukee.weather.places.di.PlaceBaseUrl
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
object PlaceNetworkConfigurationModule {

    @Provides
    @JvmStatic
    @Reusable
    @PlaceApiKey
    fun providePlacesApiKey(provider: ResourceProvider): String {
        return provider.getString(R.string.place_api_key)
    }

    @Provides
    @JvmStatic
    @Reusable
    @PlaceBaseUrl
    fun providePlaceApiBaseUrl(provider: ResourceProvider): String {
        return provider.getString(R.string.place_base_url)
    }

}