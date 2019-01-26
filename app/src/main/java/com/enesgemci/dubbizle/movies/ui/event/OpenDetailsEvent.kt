package com.enesgemci.dubbizle.movies.ui.event

import android.view.View
import com.enesgemci.dubbizle.movies.ui.model.ui.MovieUIModel

open class OpenDetailsEvent(val sharedView: View, val movieUIModel: MovieUIModel)