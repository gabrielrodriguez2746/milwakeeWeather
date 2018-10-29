package com.milwaukee.weather.base

import android.support.annotation.ArrayRes
import android.support.annotation.StringRes

interface BaseResourceProvider {

    fun getString(@StringRes id: Int): String

    fun getString(@StringRes id: Int, vararg args: Any?): String

    fun getStringArray(@ArrayRes id: Int): Array<String>

}