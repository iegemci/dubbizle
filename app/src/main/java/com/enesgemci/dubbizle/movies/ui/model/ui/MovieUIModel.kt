package com.enesgemci.dubbizle.movies.ui.model.ui

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.joda.time.DateTime

@Parcelize
data class MovieUIModel(

    var id: Int = 0,
    var voteCount: Int = 0,
    var video: Boolean = false,
    var voteAverage: Double = 0.0,
    var title: String = "",
    var popularity: Double = 0.0,
    var posterPath: String = "",
    var originalLanguage: String = "",
    var originalTitle: String = "",
    var genreIds: ArrayList<Int> = arrayListOf(),
    var backdropPath: String = "",
    var adult: Boolean = false,
    var overview: String = "",
    var releaseDate: DateTime = DateTime.now()

) : Parcelable