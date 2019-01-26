package com.enesgemci.dubbizle.moviedetail.ui.model.api

import com.enesgemci.dubbizle.network.converter.DateTimeTypeAdapter
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import org.joda.time.DateTime

data class MovieDetailsResponse(

    @SerializedName("adult")
    var adult: Boolean? = null,

    @SerializedName("backdrop_path")
    var backdropPath: String? = null,

    @SerializedName("homepage")
    var homepage: String? = null,

    @SerializedName("id")
    var id: Int? = null,

    @SerializedName("imdb_id")
    var imdbId: String? = null,

    @SerializedName("original_language")
    var originalLanguage: String? = null,

    @SerializedName("original_title")
    var originalTitle: String? = null,

    @SerializedName("overview")
    var overview: String? = null,

    @SerializedName("poster_path")
    var posterPath: String? = null,

    @JsonAdapter(DateTimeTypeAdapter::class)
    @SerializedName("release_date")
    var releaseDate: DateTime? = null,

    @SerializedName("runtime")
    var runtime: Int? = null,

    @SerializedName("status")
    var status: String? = null,

    @SerializedName("title")
    var title: String? = null
)