package com.milwaukee.weather.di.modules

import com.milwaukee.weather.landing.di.modules.LandingActivityBuilder
import dagger.Module

@Module(
    includes = [
        LandingActivityBuilder::class
    ]
)
class ActivitiesModule