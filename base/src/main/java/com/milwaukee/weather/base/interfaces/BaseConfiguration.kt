package com.milwaukee.weather.base.interfaces

/**
 * This class provide app configuration for features
 */
interface BaseConfiguration {

    /**
     * Returns if the service app logs are activated.
     * @return true if logs are active false if logs are turned off.
     */
    fun areAppLogsEnable() : Boolean

}