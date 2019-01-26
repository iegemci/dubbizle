package com.enesgemci.dubbizle.movies.ui.model.ui

data class MoviesUIModel(

    var page: Int = 0,
    var totalResults: Int = 0,
    var totalPages: Int = 0,
    var movies: ArrayList<MovieUIModel> = arrayListOf()

)