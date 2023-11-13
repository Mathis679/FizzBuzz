package com.mathislaurent.ui.form

import com.mathislaurent.core.di.modules.MainDispatcher
import com.mathislaurent.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class FizzBuzzFormViewModel @Inject constructor(
    @MainDispatcher dispatcher: CoroutineDispatcher
): BaseViewModel(dispatcher) {

}
