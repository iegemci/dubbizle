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

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.enesgemci.dubbizle.core.App
import dagger.Module
import dagger.Provides
import com.enesgemci.dubbizle.core.dagger.qualifier.ApplicationContext
import javax.inject.Singleton

/**
 * Created by Aditya on 23-Oct-16.
 */

@Module
class AppModule(var mApplication: App) {

    @Provides
    @Singleton
    fun provideApplication(): App {
        return mApplication
    }

    @Provides
    @Singleton
    @ApplicationContext
    fun provideApplicationContext(): Context {
        return mApplication.applicationContext
    }

    @Provides
    @Singleton
    fun provideConnectivityManager(): ConnectivityManager {
        return mApplication.getSystemService(Application.CONNECTIVITY_SERVICE) as ConnectivityManager
    }
}
