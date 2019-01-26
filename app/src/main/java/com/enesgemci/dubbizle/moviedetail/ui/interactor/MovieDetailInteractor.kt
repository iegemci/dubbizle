package com.enesgemci.dubbizle.movies.ui.interactor

import com.enesgemci.dubbizle.core.base.BaseInteractor
import com.enesgemci.dubbizle.core.dagger.scope.FragmentScope
import com.enesgemci.dubbizle.moviedetail.ui.mapper.MovieDetailsMapper
import com.enesgemci.dubbizle.moviedetail.ui.model.ui.MovieDetailsUIModel
import com.enesgemci.dubbizle.network.ApiKey
import com.enesgemci.dubbizle.network.TMDbApi
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import javax.inject.Inject

@FragmentScope
open class MovieDetailInteractor @Inject constructor(
    private val tmDbApi: TMDbApi,
    private val movieDetailsMapper: MovieDetailsMapper,
    private val apiKey: ApiKey
) : BaseInteractor() {

    fun getMovieDetail(movieId: Int): Deferred<MovieDetailsUIModel> {
        return async(start = CoroutineStart.LAZY) {
            val response = tmDbApi.getMovieDetails(movieId, apiKey.apiKey).await()
            return@async movieDetailsMapper.mapDetails(response)
        }
    }
}