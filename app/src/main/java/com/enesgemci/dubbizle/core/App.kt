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

package com.enesgemci.dubbizle.core

import android.app.Activity
import android.app.Application
import com.enesgemci.dubbizle.BuildConfig
import com.enesgemci.dubbizle.R
import com.enesgemci.dubbizle.core.dagger.component.AppComponent
import com.enesgemci.dubbizle.core.dagger.component.DaggerAppComponent
import com.enesgemci.dubbizle.core.dagger.module.AppModule
import com.enesgemci.dubbizle.core.dagger.module.DataModule
import com.enesgemci.dubbizle.core.navigation.NavigationDefaults
import com.enesgemci.dubbizle.core.navigation.NavigationDefaultsHolder.initDefaults
import com.enesgemci.dubbizle.core.navigation.ToolbarStyle
import com.enesgemci.dubbizle.core.navigation.ToolbarStyle.NavigationIconType.*
import timber.log.Timber

/**
 * Created by enesgemci on 09/09/15.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        initDagger()
        initNavigation()
    }

    private fun initDagger() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .dataModule(DataModule(getString(R.string.url)))
            .build()
        appComponent?.inject(this)
    }

    private fun initNavigation() {
        initDefaults(NavigationDefaults()
            .navigationIcon(BACK, R.drawable.ic_back_icon)
            .navigationIcon(CLOSE, R.drawable.ic_close_icon)
            .navigationIconListener { view ->
                view.context
                    ?.takeIf { it is Activity }
                    ?.apply {
                        (this as Activity).onBackPressed()
                    }
            })
    }

    companion object {

        var appComponent: AppComponent? = null
            private set
    }
}