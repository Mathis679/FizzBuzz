package com.mathislaurent.ui.form

import androidx.annotation.StringRes
import com.mathislaurent.core.di.modules.MainDispatcher
import com.mathislaurent.fizzbuzz.R
import com.mathislaurent.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FizzBuzzFormViewModel @Inject constructor(
    @MainDispatcher private val dispatcher: CoroutineDispatcher
): BaseViewModel(dispatcher) {

    companion object {
        const val LIMIT_CHAR_IN_WORD = 5
    }

    private val _navigateToList = MutableStateFlow<ValidForm?>(null)
    val navigateToList: StateFlow<ValidForm?> = _navigateToList

    private val _formErrorState = MutableStateFlow(FormError())
    val formErrorState: StateFlow<FormError> = _formErrorState

    fun validateForm(firstInt: String, secondInt: String, firstWord: String, secondWord: String) = launch {
        var hasError = false
        _formErrorState.emit(
            FormError(
                firstInt = validateIntField(firstInt)?.also { hasError = true },
                secondInt = validateIntField(secondInt)?.also { hasError = true },
                firstWord = validateStringField(firstWord)?.also { hasError = true },
                secondWord = validateStringField(secondWord)?.also { hasError = true }
            )
        )
        if (!hasError) {
            _navigateToList.emit(
                ValidForm(
                    firstInt = firstInt.toInt(),
                    secondInt = secondInt.toInt(),
                    firstWord = firstWord,
                    secondWord = secondWord,
                )
            )
        }
    }

    private suspend fun validateIntField(intStr: String): FormErrorType? = withContext(dispatcher) {
        when {
            intStr.isEmpty() -> FormErrorType.EMPTY
            intStr.toIntOrNull() == null -> FormErrorType.BAD_FORMAT
            else -> null
        }
    }

    private suspend fun validateStringField(word: String): FormErrorType? = withContext(dispatcher) {
        when {
            word.isEmpty() -> FormErrorType.EMPTY
            else -> null
        }
    }

    override fun catchError(error: Throwable) {
        error.printStackTrace()
    }
}

data class ValidForm(
    val firstInt: Int,
    val secondInt: Int,
    val firstWord: String,
    val secondWord: String
)

enum class FormErrorType(@StringRes val errorRes: Int) {
    BAD_FORMAT(R.string.bad_format_error),
    EMPTY(R.string.empty_error)
}

data class FormError(
    val firstInt: FormErrorType? = null,
    val secondInt: FormErrorType? = null,
    val firstWord: FormErrorType? = null,
    val secondWord: FormErrorType? = null
)
