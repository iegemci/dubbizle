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

import com.enesgemci.dubbizle.core.dagger.module.DialogFragmentModule
import com.enesgemci.dubbizle.core.dagger.scope.FragmentScope
import com.enesgemci.dubbizle.movies.ui.filter.FilterBottomSheetDialogFragment
import dagger.Component

/**
 * Created by enesgemci on 11/05/2017.
 */
@FragmentScope
@Component(dependencies = [(AppComponent::class)], modules = [(DialogFragmentModule::class)])
interface DialogFragmentComponent {

    fun inject(filterBottomSheetDialogFragment: FilterBottomSheetDialogFragment)
}