package com.milwaukee.weather

import com.milwaukee.weather.base.InjectableApplication
import com.milwaukee.weather.di.component.DaggerMilwaukeeComponent

class MilwaukeeWeatherApplication : InjectableApplication() {

    override fun initializeInjector() {
        DaggerMilwaukeeComponent
            .builder()
            .application(this)
            .build()
            .apply { inject(this@MilwaukeeWeatherApplication) }
    }
}