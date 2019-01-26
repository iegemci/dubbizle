package com.enesgemci.dubbizle.movies

import com.enesgemci.dubbizle.core.base.BaseView
import com.enesgemci.dubbizle.movies.ui.event.OpenDetailsEvent
import com.enesgemci.dubbizle.movies.ui.model.ui.MoviesUIModel

interface MoviesView : BaseView {

    fun openDetailsPage(event: OpenDetailsEvent)
}
