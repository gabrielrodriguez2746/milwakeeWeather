package com.milwaukee.weather.base.di.modules

import com.milwaukee.weather.base.di.MilwaukeeHttpClient
import com.milwaukee.weather.base.di.MilwaukeeWeatherBaseUrl
import com.milwaukee.weather.base.di.MilwaukeeWeatherService
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit

@Module
object HttpClientModule {

    @MilwaukeeHttpClient
    @Provides
    @JvmStatic
    fun provideMilwaukeeClient(interceptors: @JvmSuppressWildcards Set<Interceptor>): OkHttpClient {
        return OkHttpClient.Builder().apply {
            readTimeout(30, TimeUnit.SECONDS) // TODO This also should be configurable
            connectTimeout(30, TimeUnit.SECONDS) // TODO This also should be configurable
            interceptors.forEach {
                addInterceptor(it)
            }
        }.build()
    }

    @MilwaukeeWeatherService
    @Provides
    @JvmStatic
    fun provideMilwaukeeService(
        @MilwaukeeHttpClient httpClient: OkHttpClient,
        @MilwaukeeWeatherBaseUrl baseUrl: String
    ): Retrofit {
        return Retrofit.Builder()
            .client(httpClient)
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

}