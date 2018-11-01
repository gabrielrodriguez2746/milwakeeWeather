package com.milwaukee.weather.places.di.modules

import com.milwaukee.weather.places.mappers.MilwaukeeDetailMapper
import com.milwaukee.weather.places.mappers.MilwaukeeGeocoderMapper
import com.milwaukee.weather.places.mappers.MilwaukeeLocationMapper
import com.milwaukee.weather.places.mappers.MilwaukeePlaceMapper
import com.milwaukee.weather.places.mappers.base.DetailMapper
import com.milwaukee.weather.places.mappers.base.GeocoderMapper
import com.milwaukee.weather.places.mappers.base.LocationMapper
import com.milwaukee.weather.places.mappers.base.PlaceMapper
import com.milwaukee.weather.places.repositories.MilwaukeeDetailRepository
import com.milwaukee.weather.places.repositories.MilwaukeeGeocoderRepository
import com.milwaukee.weather.places.repositories.MilwaukeePlaceRepository
import com.milwaukee.weather.places.repositories.base.DetailRepository
import com.milwaukee.weather.places.repositories.base.GeocodeRepository
import com.milwaukee.weather.places.repositories.base.PlaceRepository
import dagger.Binds
import dagger.Module

@Module(
    includes = [
        PlaceDependenciesModule::class
    ]
)
abstract class PlaceModule {

    @Binds
    abstract fun bindGeocodeMapper(mapper: MilwaukeeGeocoderMapper): GeocoderMapper

    @Binds
    abstract fun bindLocationMapper(mapper: MilwaukeeLocationMapper): LocationMapper

    @Binds
    abstract fun bindPlaceMapper(mapper: MilwaukeePlaceMapper): PlaceMapper

    @Binds
    abstract fun bindDetailMapper(mapper: MilwaukeeDetailMapper): DetailMapper

    @Binds
    abstract fun bindDetailRepository(repository: MilwaukeeDetailRepository): DetailRepository

    @Binds
    abstract fun bindGeocoderRepository(repository: MilwaukeeGeocoderRepository): GeocodeRepository

    @Binds
    abstract fun bindPlaceRepository(repositoryV2: MilwaukeePlaceRepository): PlaceRepository

}