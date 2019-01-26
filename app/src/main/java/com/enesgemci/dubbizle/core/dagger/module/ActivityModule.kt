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

import android.content.Context
import com.enesgemci.dubbizle.core.base.BaseActivity
import dagger.Module
import dagger.Provides
import com.enesgemci.dubbizle.core.dagger.qualifier.ActivityContext

/**
 * Created by enesgemci on 11/05/2017.
 */
@Module
class ActivityModule(private val baseActivity: BaseActivity<*, *>) {

    @Provides
    @ActivityContext
    fun provideActivityContext(): Context {
        return baseActivity
    }

    @Provides
    fun provideActivity(): BaseActivity<*, *> {
        return baseActivity
    }
}