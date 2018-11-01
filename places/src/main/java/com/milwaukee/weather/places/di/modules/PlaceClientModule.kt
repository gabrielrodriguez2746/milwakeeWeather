package com.milwaukee.weather.places.di.modules

import com.google.gson.Gson
import com.milwaukee.weather.places.di.PlaceBaseUrl
import com.milwaukee.weather.places.di.PlaceHttpClient
import com.milwaukee.weather.places.di.PlaceService
import com.milwaukee.weather.places.di.modules.PlaceInterceptorModule.placeNetworkKeys
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
object PlaceClientModule {

    @PlaceHttpClient
    @Provides
    @Singleton
    @JvmStatic
    fun providePlaceClient(interceptors: @JvmSuppressWildcards Map<String, Interceptor>): OkHttpClient {
        return OkHttpClient.Builder().apply {
            readTimeout(30, TimeUnit.SECONDS) // TODO This also should be configurable
            connectTimeout(30, TimeUnit.SECONDS) // TODO This also should be configurable
            interceptors.entries.forEach {
                val key = it.key
                if (placeNetworkKeys.contains(key)) {
                    addInterceptor(it.value)
                }
            }
        }.build()
    }

    @PlaceService
    @Provides
    @Singleton
    @JvmStatic
    fun provideMilwaukeeService(
        @PlaceHttpClient httpClient: OkHttpClient,
        @PlaceBaseUrl baseUrl: String,
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