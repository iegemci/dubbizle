package com.enesgemci.dubbizle.moviedetail.ui.mapper

import com.enesgemci.dubbizle.core.dagger.scope.FragmentScope
import com.enesgemci.dubbizle.moviedetail.ui.model.api.MovieDetailsResponse
import com.enesgemci.dubbizle.moviedetail.ui.model.ui.MovieDetailsUIModel
import org.joda.time.DateTime
import javax.inject.Inject

private const val IMAGE_URL = "https://image.tmdb.org/t/p/original/"
private const val IMDB_URL = "https://www.imdb.com/title/"

@FragmentScope
class MovieDetailsMapper @Inject constructor() {

    fun mapDetails(movieDetails: MovieDetailsResponse): MovieDetailsUIModel {
        return MovieDetailsUIModel().apply {
            adult = movieDetails.adult ?: false
            backdropPath = movieDetails.backdropPath.orEmpty()
            homepage = movieDetails.homepage.orEmpty()
            id = movieDetails.id ?: 0
            imdbUrl = IMDB_URL + movieDetails.imdbId.orEmpty()
            originalLanguage = movieDetails.originalLanguage.orEmpty()
            originalTitle = movieDetails.originalTitle.orEmpty()
            overview = movieDetails.overview.orEmpty()
            posterPath = IMAGE_URL + movieDetails.posterPath.orEmpty()
            releaseDate = movieDetails.releaseDate ?: DateTime.now()
            runtime = movieDetails.runtime ?: 0
            status = movieDetails.status.orEmpty()
            title = movieDetails.title.orEmpty()
        }
    }
}