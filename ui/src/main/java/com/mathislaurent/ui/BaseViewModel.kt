package com.mathislaurent.ui

import androidx.lifecycle.ViewModel
import com.mathislaurent.core.di.modules.MainDispatcher
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel(
    @MainDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel(), CoroutineScope {

    private val exceptionHandler = CoroutineExceptionHandler { _, error ->
        if (error !is CancellationException) {
            catchError(error)
        }
    }

    private val parentJob = SupervisorJob() + exceptionHandler
    override val coroutineContext: CoroutineContext
        get() = dispatcher + parentJob

    abstract fun catchError(error: Throwable)

    override fun onCleared() {
        this.parentJob.cancel()
        super.onCleared()
    }
}
