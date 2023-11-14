package com.mathislaurent.ui.form

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mathislaurent.fizzbuzz.R
import com.mathislaurent.ui.component.LimitedTextField

@Composable
fun FizzBuzzFormScreen(
    viewModel: FizzBuzzFormViewModel,
    goToList: (Int, Int, String, String) -> Unit
) {
    val goToListState = viewModel.navigateToList.collectAsStateWithLifecycle().value
    val formErrorState = viewModel.formErrorState.collectAsStateWithLifecycle().value
    val listenNavigation = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(goToListState) {
        if (listenNavigation.value) {
            goToListState?.let {
                goToList(
                    goToListState.firstInt,
                    goToListState.secondInt,
                    goToListState.firstWord,
                    goToListState.secondWord
                )
                listenNavigation.value = false
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        FizzBuzzForm(formError = formErrorState) { firstInt, secondInt, firstWord, secondWord ->
            viewModel.validateForm(firstInt, secondInt, firstWord, secondWord)
            listenNavigation.value = true
        }
    }
}

@Composable
fun FizzBuzzForm(
    formError: FormError,
    onValidate: (String, String, String, String) -> Unit
) {
    val firstNumber = remember { mutableStateOf("") }
    val secondNumber = remember { mutableStateOf("") }
    val firstWord = remember { mutableStateOf("") }
    val secondWord = remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(vertical = 64.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LimitedTextField(
            limit = FizzBuzzFormViewModel.LIMIT_CHAR_IN_WORD,
            value = firstNumber.value,
            onValueChange = {
                firstNumber.value = it
            },
            label = { Text(stringResource(id = R.string.first_int_label)) },
            isError = formError.firstInt != null,
            error = formError.firstInt?.errorRes?.let { stringResource(id = it) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.weight(1f))
        LimitedTextField(
            limit = FizzBuzzFormViewModel.LIMIT_CHAR_IN_WORD,
            value = secondNumber.value,
            onValueChange = {
                secondNumber.value = it
            },
            label = { Text(stringResource(id = R.string.second_int_label)) },
            isError = formError.secondInt != null,
            error = formError.secondInt?.errorRes?.let { stringResource(id = it) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.weight(1f))
        LimitedTextField(
            limit = FizzBuzzFormViewModel.LIMIT_CHAR_IN_WORD,
            value = firstWord.value,
            onValueChange = {
                firstWord.value = it
            },
            label = { Text(stringResource(id = R.string.first_word_label)) },
            isError = formError.firstWord != null,
            error = formError.firstWord?.errorRes?.let { stringResource(id = it) }
        )
        Spacer(modifier = Modifier.weight(1f))
        LimitedTextField(
            limit = FizzBuzzFormViewModel.LIMIT_CHAR_IN_WORD,
            value = secondWord.value,
            onValueChange = {
                secondWord.value = it
            },
            label = { Text(stringResource(id = R.string.second_word_label)) },
            isError = formError.secondWord != null,
            error = formError.secondWord?.errorRes?.let { stringResource(id = it) }
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = {
            onValidate(
                firstNumber.value,
                secondNumber.value,
                firstWord.value,
                secondWord.value,
            )
        }) {
            Text(text = stringResource(id = R.string.validate))
        }
    }
}
