package com.enesgemci.dubbizle.movies.ui

import com.enesgemci.dubbizle.core.base.BasePresenter
import com.enesgemci.dubbizle.core.dagger.scope.FragmentScope
import com.enesgemci.dubbizle.movies.ui.event.OpenDetailsEvent
import com.enesgemci.dubbizle.movies.ui.interactor.MoviesInteractor
import com.enesgemci.dubbizle.movies.ui.model.ui.MoviesUIModel
import javax.inject.Inject

@FragmentScope
open class MoviesUIPresenter @Inject constructor(
    private val moviesInteractor: MoviesInteractor
) : BasePresenter<MoviesUIView>() {

    fun getMovies(page: Int, showLoading: Boolean, minYear: String? = null, maxYear: String? = null) {
        moviesInteractor.getPopularMovies(page, minYear, maxYear).call(
            showLoading = showLoading,
            onSuccess = {
                populateView(it)
            }
        )
    }

    fun populateView(response: MoviesUIModel) {
        ifViewAttached {
            it.setMovies(response)
        }
    }

    fun onMovieClicked(event: OpenDetailsEvent) {
        eventBus.post(event)
    }
}
