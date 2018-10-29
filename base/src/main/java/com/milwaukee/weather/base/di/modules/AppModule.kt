package com.milwaukee.weather.base.di.modules

import android.app.Application
import android.content.Context
import com.milwaukee.weather.base.InjectableApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AppModule {

    @Provides
    @Singleton
    @JvmStatic
    fun providesContext(app: InjectableApplication): Context = app.applicationContext

    @Provides
    @Singleton
    @JvmStatic
    fun providesApplication(app: InjectableApplication): Application = app

}