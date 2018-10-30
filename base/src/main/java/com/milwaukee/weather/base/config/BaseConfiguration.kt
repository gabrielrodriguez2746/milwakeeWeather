package com.milwaukee.weather.base.config

/**
 * This class provide app configuration for features
 */
interface BaseConfiguration {

    /**
     * Returns if the service app logs are acti vated.
     * @return true if logs are active false if logs are turned off.
     */
    fun areAppLogsEnable() : Boolean

}