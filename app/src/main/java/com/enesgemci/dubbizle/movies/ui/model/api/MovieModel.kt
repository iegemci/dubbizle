package com.enesgemci.dubbizle.movies.ui.model.api

import com.enesgemci.dubbizle.network.converter.DateTimeTypeAdapter
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import org.joda.time.DateTime

data class MovieModel(

    @SerializedName("id")
    var id: Int? = null,

    @SerializedName("vote_count")
    var voteCount: Int? = null,

    @SerializedName("video")
    var video: Boolean? = null,

    @SerializedName("vote_average")
    var voteAverage: Double? = null,

    @SerializedName("title")
    var title: String? = null,

    @SerializedName("popularity")
    var popularity: Double? = null,

    @SerializedName("poster_path")
    var posterPath: String? = null,

    @SerializedName("original_language")
    var originalLanguage: String? = null,

    @SerializedName("original_title")
    var originalTitle: String? = null,

    @SerializedName("genre_ids")
    var genreIds: List<Int>? = null,

    @SerializedName("backdrop_path")
    var backdropPath: String? = null,

    @SerializedName("adult")
    var adult: Boolean? = null,

    @SerializedName("overview")
    var overview: String? = null,

    @JsonAdapter(DateTimeTypeAdapter::class)
    @SerializedName("release_date")
    var releaseDate: DateTime? = null
)