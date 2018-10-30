package com.milwaukee.weather.base.di.modules

import android.util.Log
import com.milwaukee.weather.base.config.BaseConfiguration
import com.milwaukee.weather.base.di.MilwaukeeWeatherApiKey
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.multibindings.IntoSet
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor

@Module
object HttpInterceptorModule {

    private const val ACCEPT = "Accept"
    private const val CONTENT_TYPE = "Content-Type"
    private const val APPLICATION_JSON = "application/json"
    private const val APP_ID = "appid"
    private const val SERVER = "SERVER"

    @Provides
    @JvmStatic
    @Reusable
    @IntoSet
    fun provideServiceLogInterceptor(configuration: BaseConfiguration): Interceptor {
        return HttpLoggingInterceptor { message -> Log.i(SERVER, message) }
            .applyLoggingInterceptorLogs(configuration.areAppLogsEnable())
    }

    @Provides
    @JvmStatic
    @Reusable
    @IntoSet
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
    @IntoSet
    fun getMapsQueryInterceptor(@MilwaukeeWeatherApiKey apiKey: String): Interceptor {
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