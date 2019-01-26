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

import com.enesgemci.dubbizle.core.dagger.module.FragmentModule
import com.enesgemci.dubbizle.core.dagger.scope.FragmentScope
import com.enesgemci.dubbizle.moviedetail.ui.MovieDetailsFragment
import com.enesgemci.dubbizle.movies.ui.MoviesFragment
import dagger.Component

/**
 * Created by enesgemci on 11/05/2017.
 */
@FragmentScope
@Component(dependencies = [(AppComponent::class)], modules = [(FragmentModule::class)])
interface FragmentComponent {

    fun inject(moviesFragment: MoviesFragment)

    fun inject(moviesFragment: MovieDetailsFragment)
}