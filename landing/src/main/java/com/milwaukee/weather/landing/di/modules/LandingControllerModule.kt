package com.milwaukee.weather.landing.di.modules

import com.milwaukee.weather.base.di.scopes.ActivityScope
import com.milwaukee.weather.base.permissions.PermissionWrapper
import com.milwaukee.weather.landing.activities.LandingActivity
import com.milwaukee.weather.landing.controllers.LandingController
import com.milwaukee.weather.landing.controllers.MilwaukeeLandingController
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class LandingControllerModule {

    @Binds
    abstract fun bindController(controller: MilwaukeeLandingController): LandingController

    @Module
    companion object {

        @Provides
        @JvmStatic
        @ActivityScope
        fun provideActivityPermissionsWrapper(activity: LandingActivity) : PermissionWrapper {
            return activity.apply { initValues(activity) }
        }

    }

}