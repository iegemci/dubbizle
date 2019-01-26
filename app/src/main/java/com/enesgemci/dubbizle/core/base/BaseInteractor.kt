package com.enesgemci.dubbizle.core.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

abstract class BaseInteractor : CoroutineScope {

    /**
     * This is the job for all coroutines started by Interactor.
     *
     * Cancelling this job will cancel all coroutines started by Interactor.
     */
    private val interactorJob = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + interactorJob
}
