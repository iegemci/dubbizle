package com.enesgemci.dubbizle.movies

import com.enesgemci.dubbizle.core.base.BaseActivity
import com.enesgemci.dubbizle.core.dagger.component.ActivityComponent
import com.enesgemci.dubbizle.helper.IntentCreator
import com.enesgemci.dubbizle.moviedetail.MovieDetailActivity
import com.enesgemci.dubbizle.movies.ui.MoviesFragment
import com.enesgemci.dubbizle.movies.ui.event.OpenDetailsEvent

class MoviesActivity : BaseActivity<MoviesView, MoviesPresenter>(), MoviesView {

    override fun onActivityStarted() {
    }

    override fun getContainedFragment() = MoviesFragment.newInstance()

    override fun injectActivity(component: ActivityComponent) {
        component.inject(this)
    }

    override fun openDetailsPage(event: OpenDetailsEvent) {
        startActivity(
            MovieDetailActivity.newIntent(this, event.movieUIModel),
            IntentCreator.getSharedElementIntent(event.sharedView, this)
        )
        
        overridePendingTransition(0, 0)
    }
}
