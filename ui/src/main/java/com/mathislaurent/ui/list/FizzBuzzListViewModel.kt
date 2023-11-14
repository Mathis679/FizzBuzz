package com.mathislaurent.ui.list

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
    savedStateHandle: SavedStateHandle,
    getFizzBuzzListPagingUseCase: GetFizzBuzzListPagingUseCase,
    @MainDispatcher dispatcher: CoroutineDispatcher
): BaseViewModel(dispatcher) {

    private val _fizzBuzzListUiState: MutableStateFlow<PagingData<String>> =
        MutableStateFlow(PagingData.empty())
    val fizzBuzzListUiState: StateFlow<PagingData<String>> = _fizzBuzzListUiState.asStateFlow()

    init {
        val firstIntArg: Int = savedStateHandle[FIRST_INT_ARG] ?: 0
        val secondIntArg: Int = savedStateHandle[SECOND_INT_ARG] ?: 0
        val firstWordArg: String = savedStateHandle[FIRST_WORD_ARG] ?: ""
        val secondWordArg: String = savedStateHandle[SECOND_WORD_ARG] ?: ""

        launch {
            getFizzBuzzListPagingUseCase(firstIntArg, secondIntArg, firstWordArg, secondWordArg)
                .distinctUntilChanged()
                .cachedIn(this)
                .collect {
                    _fizzBuzzListUiState.emit(it)
                }
        }
    }

    override fun catchError(error: Throwable) {
        error.printStackTrace()
    }
}
