package com.mathislaurent.ui.navigation

sealed class FizzBuzzNavigationItems(val screenRoute: String) {
    object FormPage: FizzBuzzNavigationItems("form")
    object ListPage: FizzBuzzNavigationItems("list/{$FIRST_INT_ARG}/{$SECOND_INT_ARG}/{$FIRST_WORD_ARG}/{$SECOND_WORD_ARG}/") {
        fun buildRoute(firstInt: Int, secondInt: Int, firstWord: String, secondWord: String) : String {
            return this.screenRoute
                .replace("{$FIRST_INT_ARG}", "$firstInt")
                .replace("{$SECOND_INT_ARG}", "$secondInt")
                .replace("{$FIRST_WORD_ARG}", firstWord)
                .replace("{$SECOND_WORD_ARG}", secondWord)
        }
    }
}

const val FIRST_INT_ARG = "firstInt"
const val SECOND_INT_ARG = "secondInt"
const val FIRST_WORD_ARG = "firstWord"
const val SECOND_WORD_ARG = "secondWord"
