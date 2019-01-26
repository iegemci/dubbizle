package com.enesgemci.dubbizle.network

import com.enesgemci.dubbizle.moviedetail.ui.model.api.MovieDetailsResponse
import com.enesgemci.dubbizle.movies.ui.model.api.MoviesResponse
import kotlinx.coroutines.Deferred
import org.joda.time.DateTime
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val PATTERN = "yyyy-MM-dd"

interface TMDbApi {

    @GET("discover/movie")
    fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int = 1,
        @Query("primary_release_date.gte") minDate: String? = DateTime.now().minusYears(100).toString(PATTERN),
        @Query("primary_release_date.lte") maxDate: String? = DateTime.now().plusYears(100).toString(PATTERN)
    ): Deferred<MoviesResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Deferred<MovieDetailsResponse>
}