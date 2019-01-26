package com.enesgemci.dubbizle.movies.ui.model.api

import com.google.gson.annotations.SerializedName

data class MoviesResponse(

    @SerializedName("page")
    var page: Int? = null,

    @SerializedName("total_results")
    var totalResults: Int? = null,

    @SerializedName("total_pages")
    var totalPages: Int? = null,

    @SerializedName("results")
    var results: List<MovieModel>? = null
)