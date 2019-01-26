/*
 * Copyright (c) 2017.
 *
 *     "Therefore those skilled at the unorthodox
 *      are infinite as heaven and earth,
 *      inexhaustible as the great rivers.
 *      When they come to an end,
 *      they begin again,
 *      like the days and months;
 *      they die and are reborn,
 *      like the four seasons."
 *
 * - Sun Tsu, "The Art of War"
 *
 * Enes Gemci
 */

package com.enesgemci.dubbizle.core.dagger.module

import com.enesgemci.dubbizle.core.App
import com.enesgemci.dubbizle.network.ApiKey
import com.enesgemci.dubbizle.network.TMDbApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.otto.Bus
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import javax.inject.Singleton

/**
 * Created by enesgemci on 11/05/2017.
 */
@Module
class DataModule(var baseUrl: String) {

    /**
     * Cache size for http response and images
     */
    private val DISK_CACHE_SIZE = 5 * 1024 * 1024 // 5 mb

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    @Singleton
    fun provideBus(): Bus {
        return Bus()
    }

    @Provides
    @Singleton
    fun provideApiKey(app: App): ApiKey {
        return ApiKey(app)
    }

    @Provides
    @Singleton
    fun provideOkhttpClient(app: App): OkHttpClient {
        val cacheDir = File(app.cacheDir, "http")

        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
            .cache(Cache(cacheDir, DISK_CACHE_SIZE.toLong()))
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
            .build()
    }

    @Provides
    @Singleton
    fun provideTMDbApi(retrofit: Retrofit): TMDbApi {
        return retrofit.create(TMDbApi::class.java)
    }
}