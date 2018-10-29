package com.milwaukee.weather.landing.di.controllers

import javax.inject.Inject

class MilwaukeeLandingController @Inject constructor(
    permissionsWrapper: PermissionsWrapper
) : LandingController, PermissionsWrapper by permissionsWrapper {



}