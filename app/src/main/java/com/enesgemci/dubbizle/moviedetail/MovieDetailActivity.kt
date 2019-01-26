package com.enesgemci.dubbizle.moviedetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.enesgemci.dubbizle.core.base.BaseActivity
import com.enesgemci.dubbizle.core.dagger.component.ActivityComponent
import com.enesgemci.dubbizle.moviedetail.ui.MovieDetailsFragment
import com.enesgemci.dubbizle.movies.ui.model.ui.MovieUIModel

class MovieDetailActivity : BaseActivity<MovieDetailView, MovieDetailPresenter>(), MovieDetailView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.executePendingTransactions()
    }

    override fun onActivityStarted() {
    }

    override fun injectActivity(component: ActivityComponent) {
        component.inject(this)
    }

    override fun getContainedFragment(): Fragment {
        return MovieDetailsFragment.newInstance(intent.getParcelableExtra(MOVIE))
    }

    companion object {

        private const val MOVIE = "MOVIE"

        fun newIntent(context: Context, movieUIModel: MovieUIModel): Intent {
            return Intent(context, MovieDetailActivity::class.java).apply {
                putExtra(MOVIE, movieUIModel)
            }
        }
    }
}