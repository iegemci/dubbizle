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

package com.enesgemci.dubbizle.core.dagger.component

import com.enesgemci.dubbizle.core.App
import com.enesgemci.dubbizle.core.dagger.module.AppModule
import com.enesgemci.dubbizle.core.dagger.module.DataModule
import com.enesgemci.dubbizle.network.ApiKey
import com.enesgemci.dubbizle.network.TMDbApi
import com.google.gson.Gson
import com.squareup.otto.Bus
import dagger.Component
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by enesgemci on 08/04/2017.
 */
@Singleton
@Component(modules = [(AppModule::class), (DataModule::class)])
interface AppComponent {

    fun inject(app: App)

    val application: App

    fun provideApiKey(): ApiKey

    fun provideGson(): Gson

    fun provideBus(): Bus

    fun provideOkhttpClient(): OkHttpClient

    fun provideRetrofit(): Retrofit

    fun provideApiService(): TMDbApi
}