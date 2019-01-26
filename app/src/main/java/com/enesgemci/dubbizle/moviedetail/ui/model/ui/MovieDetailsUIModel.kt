package com.enesgemci.dubbizle.moviedetail.ui.model.ui

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.joda.time.DateTime

@Parcelize
open class MovieDetailsUIModel(
    var adult: Boolean = false,
    var backdropPath: String = "",
    var homepage: String = "",
    var id: Int = 0,
    var imdbUrl: String = "",
    var originalLanguage: String = "",
    var originalTitle: String = "",
    var overview: String = "",
    var posterPath: String = "",
    var releaseDate: DateTime = DateTime.now(),
    var runtime: Int = 0,
    var status: String = "",
    var title: String = ""
) : Parcelable