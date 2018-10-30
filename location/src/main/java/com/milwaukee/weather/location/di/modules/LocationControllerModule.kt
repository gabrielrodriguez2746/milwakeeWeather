package com.milwaukee.weather.location.di.modules

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.milwaukee.weather.base.location.LocationProvider
import com.milwaukee.weather.location.provider.MilwaukeeLocationController
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
abstract class LocationControllerModule {

    @Binds
    abstract fun bindLocationProvider(locationController: MilwaukeeLocationController): LocationProvider

    @Module
    companion object {

        @Provides
        @JvmStatic
        @Reusable
        fun provideFusedLocationProvider(context: Context): FusedLocationProviderClient {
            return LocationServices.getFusedLocationProviderClient(context)
        }

    }
}