package com.milwaukee.weather.base.di.modules

import android.util.Log
import com.milwaukee.weather.base.config.BaseConfiguration
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor

@Module
object BaseInterceptorModule {

    const val APP_LOG_INTERCEPTOR = "APP_LOG_INTERCEPTOR"
    const val HEADERS_INTERCEPTOR = "HEADERS_INTERCEPTOR"

    private const val ACCEPT = "Accept"
    private const val CONTENT_TYPE = "Content-Type"
    private const val APPLICATION_JSON = "application/json"
    private const val SERVER = "SERVER"

    @Provides
    @JvmStatic
    @Reusable
    @IntoMap
    @StringKey(APP_LOG_INTERCEPTOR)
    fun provideLanguageInterceptor(configuration: BaseConfiguration): Interceptor {
        return HttpLoggingInterceptor { message -> Log.e(SERVER, message) }
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
                .header(
                    ACCEPT,
                    APPLICATION_JSON
                )
                .header(
                    CONTENT_TYPE,
                    APPLICATION_JSON
                )
                .method(chain.request().method(), chain.request().body())
            chain.proceed(requestBuilder.build())
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

}