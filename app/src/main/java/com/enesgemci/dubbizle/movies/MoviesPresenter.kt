package com.enesgemci.dubbizle.movies

import com.enesgemci.dubbizle.core.base.BasePresenter
import com.enesgemci.dubbizle.core.dagger.scope.ActivityScope
import com.enesgemci.dubbizle.movies.ui.event.OpenDetailsEvent
import com.squareup.otto.Subscribe
import javax.inject.Inject

@ActivityScope
class MoviesPresenter @Inject constructor() : BasePresenter<MoviesView>() {

    @Subscribe
    fun onEvent(event: OpenDetailsEvent) {
        ifViewAttached {
            it.openDetailsPage(event)
        }
    }
}
