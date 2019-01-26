package com.enesgemci.dubbizle.moviedetail.ui

import com.enesgemci.dubbizle.core.base.BaseView
import com.enesgemci.dubbizle.moviedetail.ui.model.ui.MovieDetailsUIModel

interface MovieDetailsUIView : BaseView {

    fun setMovieDetails(movieDetailsUIModel: MovieDetailsUIModel)
}
