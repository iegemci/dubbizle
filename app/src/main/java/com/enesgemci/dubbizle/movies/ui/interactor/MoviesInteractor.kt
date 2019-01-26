package com.enesgemci.dubbizle.movies.ui.interactor

import com.enesgemci.dubbizle.core.base.BaseInteractor
import com.enesgemci.dubbizle.core.dagger.scope.FragmentScope
import com.enesgemci.dubbizle.movies.ui.mapper.MoviesMapper
import com.enesgemci.dubbizle.movies.ui.model.ui.MoviesUIModel
import com.enesgemci.dubbizle.network.ApiKey
import com.enesgemci.dubbizle.network.TMDbApi
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import javax.inject.Inject

@FragmentScope
open class MoviesInteractor @Inject constructor(
    private val tmDbApi: TMDbApi,
    private val moviesMapper: MoviesMapper,
    private val apiKey: ApiKey
) : BaseInteractor() {

    fun getPopularMovies(page: Int = 1, minYear: String?, maxYear: String?): Deferred<MoviesUIModel> {
        return async(start = CoroutineStart.LAZY) {
            val response = tmDbApi.getPopularMovies(apiKey.apiKey, page, minYear, maxYear).await()
            return@async moviesMapper.mapMovies(response)
        }
    }
}