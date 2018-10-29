package com.milwaukee.weather

import com.milwaukee.weather.base.BaseConfiguration
import javax.inject.Inject

class AppConfiguration @Inject constructor() : BaseConfiguration {

    override fun areAppLogsEnable() = BuildConfig.SERVICE_LOGS_FLAG

}