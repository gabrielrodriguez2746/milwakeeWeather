package com.milwaukee.weather.places.di.modules

import android.content.res.Resources
import android.support.v4.os.ConfigurationCompat
import com.milwaukee.weather.base.di.modules.BaseInterceptorModule.APP_LOG_INTERCEPTOR
import com.milwaukee.weather.base.helpers.getNewRequest
import com.milwaukee.weather.places.di.PlaceApiKey
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import okhttp3.Interceptor

@Module
object PlaceInterceptorModule {

    private const val PLACE_KEY_INTERCEPTOR = "PLACE_KEY_INTERCEPTOR"
    private const val PLACE_LANGUAGE_INTERCEPTOR = "PLACE_LANGUAGE_INTERCEPTOR"
    val placeNetworkKeys = setOf(
        PLACE_KEY_INTERCEPTOR,
        APP_LOG_INTERCEPTOR,
        PLACE_LANGUAGE_INTERCEPTOR
    )

    private const val KEY = "key"
    private const val LANGUAGE = "language"

    @Provides
    @JvmStatic
    @Reusable
    @IntoMap
    @StringKey(PLACE_KEY_INTERCEPTOR)
    fun providePlaceKeyInterceptor(@PlaceApiKey apiKey: String): Interceptor {
        return Interceptor {
            it.proceed(it.request().getNewRequest(KEY, apiKey))
        }
    }

    @Provides
    @JvmStatic
    @Reusable
    @IntoMap
    @StringKey(PLACE_LANGUAGE_INTERCEPTOR)
    fun provideServiceLogInterceptor(): Interceptor {
        return Interceptor {
            val language = ConfigurationCompat.getLocales(Resources.getSystem().configuration).get(0)
                .language.toLowerCase()
            it.proceed(it.request())
            it.proceed(it.request().getNewRequest(LANGUAGE, language))
        }
    }

}