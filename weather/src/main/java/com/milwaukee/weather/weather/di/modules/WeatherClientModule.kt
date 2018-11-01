package com.milwaukee.weather.weather.di.modules

import com.google.gson.Gson
import com.milwaukee.weather.weather.di.WeatherBaseUrl
import com.milwaukee.weather.weather.di.WeatherClient
import com.milwaukee.weather.weather.di.WeatherService
import com.milwaukee.weather.weather.di.modules.WeatherInterceptorModule.appNetworkKeys
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
object WeatherClientModule {

    @WeatherClient
    @Provides
    @Singleton
    @JvmStatic
    fun provideMilwaukeeClient(interceptors: @JvmSuppressWildcards Map<String, Interceptor>): OkHttpClient {
        return OkHttpClient.Builder().apply {
            readTimeout(30, TimeUnit.SECONDS) // TODO This also should be configurable
            connectTimeout(30, TimeUnit.SECONDS) // TODO This also should be configurable
            interceptors.entries.forEach {
                val key = it.key
                if (appNetworkKeys.contains(key)) {
                    addInterceptor(it.value)
                }
            }
        }.build()
    }

    @WeatherService
    @Provides
    @Singleton
    @JvmStatic
    fun provideMilwaukeeService(
        @WeatherClient httpClient: OkHttpClient,
        @WeatherBaseUrl baseUrl: String,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .client(httpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

}