package com.enesgemci.dubbizle.moviedetail.ui

import com.enesgemci.dubbizle.moviedetail.ui.model.ui.MovieDetailsUIModel
import com.enesgemci.dubbizle.movies.ui.interactor.MovieDetailInteractor
import com.squareup.otto.Bus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import kotlin.coroutines.CoroutineContext

class MovieDetailsUIPresenterTest : CoroutineScope {

    private val testJob = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + testJob

    private val mockView: MovieDetailsUIView = Mockito.mock(MovieDetailsUIView::class.java)

    private val movieDetailInteractor = Mockito.mock(MovieDetailInteractor::class.java)

    private val mockPresenter = Mockito.spy(MovieDetailsUIPresenter(movieDetailInteractor))

    private val movieDetailsUIModel: MovieDetailsUIModel = MovieDetailsUIModel()

    private val eventBusMock: Bus = Mockito.mock(Bus::class.java)

    @Before
    fun setup() {
        mockPresenter.eventBus = eventBusMock
    }

    @Test
    fun getMovieDetail_noQuery() {
        launch {
            mockPresenter.getMovieDetails(0)

            Mockito.verify(movieDetailInteractor, Mockito.never())
                .getMovieDetail(0)
                .await()
        }
    }

    @Test
    fun getMovieDetail() {
        launch {
            mockPresenter.getMovieDetails(399402)

            Mockito.verify(movieDetailInteractor, Mockito.times(1))
                .getMovieDetail(399402)
                .await()
        }
    }

    @Test
    fun populateView() {
        mockPresenter.attachView(mockView)

        mockPresenter.populateView(movieDetailsUIModel)

        Mockito.verify(mockView).setMovieDetails(movieDetailsUIModel)
    }

    @Test
    fun checkPopulateWhenViewNotAttached() {
        mockPresenter.populateView(movieDetailsUIModel)

        Mockito.verify(mockView, Mockito.never()).setMovieDetails(movieDetailsUIModel)
    }
}