package com.milwaukee.weather.weather.di.modules

import android.content.res.Resources
import android.support.v4.os.ConfigurationCompat
import com.milwaukee.weather.base.di.modules.BaseInterceptorModule.APP_LOG_INTERCEPTOR
import com.milwaukee.weather.base.di.modules.BaseInterceptorModule.HEADERS_INTERCEPTOR
import com.milwaukee.weather.base.helpers.getNewRequest
import com.milwaukee.weather.weather.di.WeatherApiKey
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import okhttp3.Interceptor

@Module
object WeatherInterceptorModule {

    private const val KEY_INTERCEPTOR = "KEY_INTERCEPTOR"
    private const val LANGUAGE_INTERCEPTOR = "LANGUAGE_INTERCEPTOR"

    val appNetworkKeys = setOf(
        APP_LOG_INTERCEPTOR,
        HEADERS_INTERCEPTOR,
        KEY_INTERCEPTOR,
        LANGUAGE_INTERCEPTOR
    )

    private const val APP_ID = "appid"
    private const val LANGUAGE = "lang"

    @Provides
    @JvmStatic
    @Reusable
    @IntoMap
    @StringKey(KEY_INTERCEPTOR)
    fun provideKeyInterceptor(@WeatherApiKey apiKey: String): Interceptor {
        return Interceptor {
            it.proceed(it.request().getNewRequest(APP_ID, apiKey))
        }
    }

    @Provides
    @JvmStatic
    @Reusable
    @IntoMap
    @StringKey(LANGUAGE_INTERCEPTOR)
    fun provideServiceLogInterceptor(): Interceptor {
        return Interceptor {
            val language = ConfigurationCompat.getLocales(Resources.getSystem().configuration).get(0)
                .language.toLowerCase()
            it.proceed(it.request())
            it.proceed(it.request().getNewRequest(LANGUAGE, language))
        }
    }

}