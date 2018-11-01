package com.milwaukee.weather.places.di.modules

import com.milwaukee.weather.places.di.PlaceService
import com.milwaukee.weather.places.rest.AutocompleteService
import com.milwaukee.weather.places.rest.GeocodeService
import com.milwaukee.weather.places.rest.PlaceDetailService
import dagger.Module
import dagger.Provides
import dagger.Reusable
import retrofit2.Retrofit

@Module
object PlacesApiModule {

    @Provides
    @Reusable
    @JvmStatic
    fun providePlacesService(@PlaceService retrofit: Retrofit): AutocompleteService {
        return retrofit.create(AutocompleteService::class.java)
    }

    @Provides
    @Reusable
    @JvmStatic
    fun providePlacesDetailService(@PlaceService retrofit: Retrofit): PlaceDetailService {
        return retrofit.create(PlaceDetailService::class.java)
    }

    @Provides
    @Reusable
    @JvmStatic
    fun providePlacesGeocodeService(@PlaceService retrofit: Retrofit): GeocodeService {
        return retrofit.create(GeocodeService::class.java)
    }
}