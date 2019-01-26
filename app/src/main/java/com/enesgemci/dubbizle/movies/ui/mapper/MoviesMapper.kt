package com.enesgemci.dubbizle.movies.ui.mapper

import com.enesgemci.dubbizle.core.dagger.scope.FragmentScope
import com.enesgemci.dubbizle.core.extension.value
import com.enesgemci.dubbizle.movies.ui.model.api.MovieModel
import com.enesgemci.dubbizle.movies.ui.model.api.MoviesResponse
import com.enesgemci.dubbizle.movies.ui.model.ui.MovieUIModel
import com.enesgemci.dubbizle.movies.ui.model.ui.MoviesUIModel
import org.joda.time.DateTime
import javax.inject.Inject

private const val IMAGE_URL = "https://image.tmdb.org/t/p/original/"

@FragmentScope
class MoviesMapper @Inject constructor() {

    fun mapMovies(response: MoviesResponse): MoviesUIModel {
        return MoviesUIModel().apply {
            page = response.page ?: 1
            totalResults = response.totalResults ?: 0
            totalPages = response.totalPages ?: 0
            movies = map(response.results)
        }
    }

    private fun map(results: List<MovieModel>?) =
        results?.map {
            MovieUIModel().apply {
                id = it.id ?: 0
                voteCount = it.voteCount ?: 0
                video = it.video ?: false
                voteAverage = it.voteAverage ?: 0.0
                title = it.title.orEmpty()
                popularity = it.popularity ?: 0.0
                posterPath = IMAGE_URL + it.posterPath.orEmpty()
                originalLanguage = it.originalLanguage.orEmpty()
                originalTitle = it.originalTitle.orEmpty()
                genreIds = it.genreIds?.map { id -> id }.value
                backdropPath = IMAGE_URL + it.backdropPath.orEmpty()
                adult = it.adult ?: false
                overview = it.overview.orEmpty()
                releaseDate = it.releaseDate ?: DateTime.now()
            }
        }.value
}