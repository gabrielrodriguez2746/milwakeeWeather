package com.milwaukee.weather.landing.di.modules

import com.milwaukee.weather.landing.di.controllers.LandingController
import com.milwaukee.weather.landing.di.controllers.MilwaukeeLandingController
import com.milwaukee.weather.landing.di.controllers.MilwaukeePermissionsWrapper
import com.milwaukee.weather.landing.di.controllers.PermissionsWrapper
import dagger.Binds
import dagger.Module

@Module
abstract class LandingContorllerModule {

    @Binds
    abstract fun bindController(controller: MilwaukeeLandingController): LandingController

    @Binds
    abstract fun bindPermissionWrapper(permissionsWrapper: MilwaukeePermissionsWrapper): PermissionsWrapper

}