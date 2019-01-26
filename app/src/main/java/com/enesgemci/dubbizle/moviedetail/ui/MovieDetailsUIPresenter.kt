package com.enesgemci.dubbizle.moviedetail.ui

import com.enesgemci.dubbizle.core.base.BasePresenter
import com.enesgemci.dubbizle.moviedetail.ui.model.ui.MovieDetailsUIModel
import com.enesgemci.dubbizle.movies.ui.interactor.MovieDetailInteractor
import javax.inject.Inject

open class MovieDetailsUIPresenter @Inject constructor(
    private val movieDetailInteractor: MovieDetailInteractor
) : BasePresenter<MovieDetailsUIView>() {

    fun getMovieDetails(movieId: Int) {
        movieDetailInteractor.getMovieDetail(movieId).call(
            showLoading = false,
            onSuccess = {
                populateView(it)
            }
        )
    }

    fun populateView(response: MovieDetailsUIModel) {
        ifViewAttached {
            it.setMovieDetails(response)
        }
    }
}
