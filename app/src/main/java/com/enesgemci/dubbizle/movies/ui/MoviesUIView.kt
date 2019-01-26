package com.enesgemci.dubbizle.movies.ui

import com.enesgemci.dubbizle.core.base.BaseView
import com.enesgemci.dubbizle.movies.ui.model.ui.MoviesUIModel

interface MoviesUIView : BaseView {

    fun setMovies(moviesUIModel: MoviesUIModel)
}
