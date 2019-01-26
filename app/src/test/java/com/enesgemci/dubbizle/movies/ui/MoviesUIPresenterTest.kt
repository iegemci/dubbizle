package com.enesgemci.dubbizle.movies.ui

import com.enesgemci.dubbizle.movies.ui.interactor.MoviesInteractor
import com.enesgemci.dubbizle.movies.ui.model.ui.MoviesUIModel
import com.squareup.otto.Bus
import kotlinx.coroutines.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import kotlin.coroutines.CoroutineContext

class MoviesUIPresenterTest : CoroutineScope {

    private val testJob = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + testJob

    private val mockView: MoviesUIView = Mockito.mock(MoviesUIView::class.java)

    private val moviesInteractor = Mockito.mock(MoviesInteractor::class.java)

    private val mockPresenter = Mockito.spy(MoviesUIPresenter(moviesInteractor))

    private val moviesUIModel: MoviesUIModel = MoviesUIModel()

    private val eventBusMock: Bus = Mockito.mock(Bus::class.java)

    @Before
    fun setup() {
        mockPresenter.eventBus = eventBusMock
    }

    @Test
    fun getMovies_noQuery() {
        launch {
            mockPresenter.getMovies(0, false)

            Mockito.verify(moviesInteractor, Mockito.never())
                .getPopularMovies(0, null, null)
                .await()
        }
    }

    @Test
    fun getMovies() {
        launch {
            mockPresenter.getMovies(1, false)

            Mockito.verify(moviesInteractor, Mockito.times(1))
                .getPopularMovies(1, "2000", "2222")
                .await()
        }
    }

    @Test
    fun populateView() {
        mockPresenter.attachView(mockView)

        mockPresenter.populateView(moviesUIModel)

        Mockito.verify(mockView).setMovies(moviesUIModel)
    }

    @Test
    fun checkPopulateWhenViewNotAttached() {
        mockPresenter.populateView(moviesUIModel)

        Mockito.verify(mockView, Mockito.never()).setMovies(moviesUIModel)
    }
}