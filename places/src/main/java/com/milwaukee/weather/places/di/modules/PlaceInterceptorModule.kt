package com.milwaukee.weather.places.di.modules

import com.milwaukee.weather.base.di.modules.HttpInterceptorModule.APP_LOG_INTERCEPTOR
import com.milwaukee.weather.places.di.PlaceApiKey
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request

@Module
object PlaceInterceptorModule {

    private const val PLACE_KEY_INTERCEPTOR = "PLACE_KEY_INTERCEPTOR"
    private const val KEY = "key"
    val placeNetworkKeys = setOf(PLACE_KEY_INTERCEPTOR, APP_LOG_INTERCEPTOR)

    @Provides
    @JvmStatic
    @Reusable
    @IntoMap
    @StringKey(PLACE_KEY_INTERCEPTOR)
    fun providePlaceKeyInterceptor(@PlaceApiKey apiKey: String): Interceptor {
        return Interceptor {
            it.proceed(it.request().getNewRequest(apiKey))
        }
    }

    private fun Request.getNewRequest(apiKey: String): Request {
        return newBuilder()
            .url(buildNewUrl(apiKey)).build()
    }

    private fun Request.buildNewUrl(apiKey: String): HttpUrl {
        return url().newBuilder().addQueryParameter(KEY, apiKey).build()
    }

}