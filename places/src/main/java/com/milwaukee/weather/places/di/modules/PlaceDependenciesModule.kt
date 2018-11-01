package com.milwaukee.weather.places.di.modules

import com.milwaukee.weather.base.places.QueryTypes
import dagger.Module
import dagger.Provides
import dagger.Reusable
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

@Module
object PlaceDependenciesModule {

    @Provides
    @JvmStatic
    @Reusable
    fun getDecimalFormat(): DecimalFormat {
        val symbolFormatter = DecimalFormatSymbols()
        symbolFormatter.decimalSeparator = ','
        return DecimalFormat("#.#######", symbolFormatter).apply { isGroupingUsed = false }
    }

    @Provides
    @JvmStatic
    @QueryTypes
    @Reusable
    fun getQueryTypes(): String {
        return "types:(regions)"
    }

}