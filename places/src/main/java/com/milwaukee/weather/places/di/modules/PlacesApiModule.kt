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
    fun getPlacesService(@PlaceService retrofit: Retrofit): AutocompleteService {
        return retrofit.create(AutocompleteService::class.java)
    }

    @Provides
    @Reusable
    @JvmStatic
    fun getPlacesDetailService(@PlaceService retrofit: Retrofit): PlaceDetailService {
        return retrofit.create(PlaceDetailService::class.java)
    }

    @Provides
    @Reusable
    @JvmStatic
    fun getPlacesGeocodeService(@PlaceService retrofit: Retrofit): GeocodeService {
        return retrofit.create(GeocodeService::class.java)
    }
}