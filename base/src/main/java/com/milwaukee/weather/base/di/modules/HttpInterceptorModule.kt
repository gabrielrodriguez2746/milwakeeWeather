package com.milwaukee.weather.base.di.modules

import android.util.Log
import com.milwaukee.weather.base.config.BaseConfiguration
import com.milwaukee.weather.base.di.MilwaukeeWeatherApiKey
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.multibindings.IntoMap
import dagger.multibindings.IntoSet
import dagger.multibindings.StringKey
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor

@Module
object HttpInterceptorModule {

    const val APP_LOG_INTERCEPTOR = "APP_LOG_INTERCEPTOR"
    private const val HEADERS_INTERCEPTOR = "HEADERS_INTERCEPTOR"
    private const val KEY_INTERCEPTOR = "KEY_INTERCEPTOR"

    val appNetworkKeys = setOf(APP_LOG_INTERCEPTOR, HEADERS_INTERCEPTOR, KEY_INTERCEPTOR)

    private const val ACCEPT = "Accept"
    private const val CONTENT_TYPE = "Content-Type"
    private const val APPLICATION_JSON = "application/json"
    private const val APP_ID = "appid"
    private const val SERVER = "SERVER"

    @Provides
    @JvmStatic
    @Reusable
    @IntoMap
    @StringKey(APP_LOG_INTERCEPTOR)
    fun provideServiceLogInterceptor(configuration: BaseConfiguration): Interceptor {
        return HttpLoggingInterceptor { message -> Log.i(SERVER, message) }
            .applyLoggingInterceptorLogs(configuration.areAppLogsEnable())
    }

    @Provides
    @JvmStatic
    @Reusable
    @IntoMap
    @StringKey(HEADERS_INTERCEPTOR)
    fun provideHeadersInterceptor(): Interceptor {
        return Interceptor { chain ->
            val requestBuilder = chain.request().newBuilder()

            requestBuilder
                .header(ACCEPT, APPLICATION_JSON)
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .method(chain.request().method(), chain.request().body())
            chain.proceed(requestBuilder.build())
        }
    }

    @Provides
    @JvmStatic
    @Reusable
    @IntoMap
    @StringKey(KEY_INTERCEPTOR)
    fun povideKeyInterceptor(@MilwaukeeWeatherApiKey apiKey: String): Interceptor {
        return Interceptor {
            it.proceed(it.request().getNewRequest(apiKey))
        }
    }

    private fun HttpLoggingInterceptor.applyLoggingInterceptorLogs(enableLogs: Boolean): HttpLoggingInterceptor {
        return apply {
            level = if (enableLogs) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    private fun Request.getNewRequest(apiKey: String): Request {
        return newBuilder()
            .url(buildNewUrl(apiKey)).build()
    }

    private fun Request.buildNewUrl(apiKey: String): HttpUrl {
        return url().newBuilder().addQueryParameter(APP_ID, apiKey).build()
    }

}