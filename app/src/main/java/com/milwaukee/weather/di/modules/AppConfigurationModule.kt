package com.milwaukee.weather.di.modules

import com.milwaukee.weather.base.config.BaseConfiguration
import com.milwaukee.weather.base.providers.ResourceProvider
import com.milwaukee.weather.providers.MilwaukeeResourceProvider
import com.milwaukee.weather.utils.AppConfiguration
import dagger.Binds
import dagger.Module

@Module
abstract class AppConfigurationModule {

    @Binds
    abstract fun bindAppConfiguration(configuration: AppConfiguration): BaseConfiguration

    @Binds
    abstract fun bindResourceProvider(resourceProvider: MilwaukeeResourceProvider): ResourceProvider
}