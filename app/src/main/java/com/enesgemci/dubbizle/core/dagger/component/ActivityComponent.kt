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

import com.enesgemci.dubbizle.core.dagger.module.ActivityModule
import com.enesgemci.dubbizle.core.dagger.scope.ActivityScope
import com.enesgemci.dubbizle.moviedetail.MovieDetailActivity
import com.enesgemci.dubbizle.movies.MoviesActivity
import dagger.Component

/**
 * Created by enesgemci on 11/05/2017.
 */
@ActivityScope
@Component(dependencies = [(AppComponent::class)], modules = [(ActivityModule::class)])
interface ActivityComponent {

    fun inject(moviesActivity: MoviesActivity)

    fun inject(movieDetailActivity: MovieDetailActivity)
}