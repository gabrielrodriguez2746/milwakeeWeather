package com.milwaukee.weather.utils

import com.milwaukee.weather.BuildConfig
import com.milwaukee.weather.base.config.BaseConfiguration
import javax.inject.Inject

class AppConfiguration @Inject constructor() : BaseConfiguration {

    override fun areAppLogsEnable() = BuildConfig.SERVICE_LOGS_FLAG

}