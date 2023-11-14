package com.mathislaurent.ui.list

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.SavedStateHandle
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mathislaurent.core.di.modules.MainDispatcher
import com.mathislaurent.domain.usecase.GetFizzBuzzListPagingUseCase
import com.mathislaurent.ui.BaseViewModel
import com.mathislaurent.ui.navigation.FIRST_INT_ARG
import com.mathislaurent.ui.navigation.FIRST_WORD_ARG
import com.mathislaurent.ui.navigation.SECOND_INT_ARG
import com.mathislaurent.ui.navigation.SECOND_WORD_ARG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FizzBuzzListViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getFizzBuzzListPagingUseCase: GetFizzBuzzListPagingUseCase,
    @MainDispatcher dispatcher: CoroutineDispatcher
): BaseViewModel(dispatcher) {

    private val _fizzBuzzListUiState: MutableStateFlow<PagingData<String>> =
        MutableStateFlow(PagingData.empty())
    val fizzBuzzListUiState: StateFlow<PagingData<String>> = _fizzBuzzListUiState.asStateFlow()

    @VisibleForTesting
    var firstIntArg: Int = savedStateHandle[FIRST_INT_ARG] ?: 0
    @VisibleForTesting
    var secondIntArg: Int = savedStateHandle[SECOND_INT_ARG] ?: 0
    @VisibleForTesting
    var firstWordArg: String = savedStateHandle[FIRST_WORD_ARG] ?: ""
    @VisibleForTesting
    var secondWordArg: String = savedStateHandle[SECOND_WORD_ARG] ?: ""

    init {
        loadData()
    }

    @VisibleForTesting
    fun loadData() = launch {
        getFizzBuzzListPagingUseCase(firstIntArg, secondIntArg, firstWordArg, secondWordArg)
            .distinctUntilChanged()
            .cachedIn(this)
            .collect {
                _fizzBuzzListUiState.emit(it)
            }
    }

    override fun catchError(error: Throwable) {
        error.printStackTrace()
    }
}
