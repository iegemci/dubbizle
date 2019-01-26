package com.enesgemci.dubbizle.core.base

import com.enesgemci.dubbizle.network.NoConnectionException
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import com.squareup.otto.Bus
import kotlinx.coroutines.*
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

abstract class BasePresenter<V : BaseView> : MvpBasePresenter<V>(), CoroutineScope {

    @Inject
    lateinit var eventBus: Bus

    /**
     * This is the job for all coroutines started by this Presenter.
     *
     * Cancelling this job will cancel all coroutines started by this Presenter.
     */
    private var presenterJob = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + presenterJob

    private suspend fun constructConsumerError(
        throwable: Throwable,
        onError: ((errorMessage: String) -> Unit)? = null
    ) {
        withContext(Dispatchers.Main) {
            onError?.invoke(throwable.message.orEmpty())

            ifViewAttached {
                it.showMessage(throwable.message.orEmpty())
            }
        }
    }

    override fun attachView(view: V) {
        super.attachView(view)
        eventBus.register(this)
    }

    override fun detachView() {
        super.detachView()
        coroutineContext.cancelChildren()
        eventBus.unregister(this)
    }

    fun <T> Deferred<T>.call(
        showLoading: Boolean = true,
        onError: ((error: String) -> Unit)? = null,
        onSuccess: (response: T) -> Unit
    ) {

        ifViewAttached {
            if (!it.isConnected) {
                // TODO: show error
                throw NoConnectionException("No internet connection")
            }
        }

        if (showLoading) {
            showLoading()
        }

        launch {
            try {
                yield()
                val mappedResponse = await()
                yield()

                withContext(Dispatchers.Main) {
                    onSuccess(mappedResponse)
                }
            } catch (ex: CancellationException) {
                Timber.e(ex)
            } catch (e: Throwable) {
                Timber.e(e)
                constructConsumerError(e, onError = onError)
            } finally {
                withContext(Dispatchers.Main) {
                    hideLoading()
                }
            }
        }
    }

    private fun showLoading() {
        ifViewAttached {
            it.showLoadingDialog()
        }
    }

    private fun hideLoading() {
        ifViewAttached {
            it.dismissLoadingDialog()
        }
    }
}
