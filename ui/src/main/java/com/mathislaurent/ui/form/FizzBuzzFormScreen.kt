package com.mathislaurent.ui.form

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun FizzBuzzFormScreen(
    viewModel: FizzBuzzFormViewModel,
    goToList: (Int, Int, String, String) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        FizzBuzzForm() { firstInt, secondInt, firstWord, secondWord ->
            // todo call verify viewModel
            goToList(firstInt, secondInt, firstWord, secondWord)
        }
    }
}

@Composable
fun FizzBuzzForm(
    onValidate: (Int, Int, String, String) -> Unit
) {
    val firstNumber = remember {
        mutableStateOf(0)
    }
    val secondNumber = remember {
        mutableStateOf(0)
    }
    val firstWord = remember {
        mutableStateOf("")
    }
    val secondWord = remember {
        mutableStateOf("")
    }
    Column {
        BasicTextField(
            value = firstNumber.value.toString(),
            onValueChange = {
                firstNumber.value = it.toIntOrNull() ?: 0
            }
        )
        BasicTextField(
            value = secondNumber.value.toString(),
            onValueChange = {
                secondNumber.value = it.toIntOrNull() ?: 0
            }
        )
        BasicTextField(
            value = firstWord.value,
            onValueChange = {
                firstWord.value = it
            }
        )
        BasicTextField(
            value = secondWord.value,
            onValueChange = {
                secondWord.value = it
            }
        )
        Button(onClick = {
            onValidate(
                firstNumber.value,
                secondNumber.value,
                firstWord.value,
                secondWord.value,
            )
        }) {
            // todo strings res
            Text(text = "Valider")
        }
    }
}
