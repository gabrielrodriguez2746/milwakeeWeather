package com.milwaukee.weather.providers

import android.content.Context
import com.milwaukee.weather.base.providers.ResourceProvider
import javax.inject.Inject

class MilwaukeeResourceProvider @Inject constructor(
    private val context: Context
) : ResourceProvider {

    override fun getString(id: Int): String = context.getString(id)

    override fun getString(id: Int, vararg args: Any?): String = context.getString(id, args)

    override fun getStringArray(id: Int): Array<String> = context.resources.getStringArray(id)
}