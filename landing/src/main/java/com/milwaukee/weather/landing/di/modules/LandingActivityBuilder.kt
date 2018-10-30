package com.milwaukee.weather.landing.di.modules

import com.milwaukee.weather.base.di.scopes.ActivityScope
import com.milwaukee.weather.landing.activities.LandingActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class LandingActivityBuilder {

    @ContributesAndroidInjector(
        modules = [
            LandingControllerModule::class
        ]
    )
    @ActivityScope
    abstract fun binLandindActivity(): LandingActivity

}