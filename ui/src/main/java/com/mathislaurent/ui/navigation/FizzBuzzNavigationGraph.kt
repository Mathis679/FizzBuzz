package com.mathislaurent.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mathislaurent.ui.form.FizzBuzzFormScreen
import com.mathislaurent.ui.form.FizzBuzzFormViewModel
import com.mathislaurent.ui.list.FizzBuzzListScreen
import com.mathislaurent.ui.list.FizzBuzzListViewModel

@Composable
fun FizzBuzzNavigationGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = FizzBuzzNavigationItems.FormPage.screenRoute
    ) {
        composable(route = FizzBuzzNavigationItems.FormPage.screenRoute) {
            val viewModel: FizzBuzzFormViewModel = hiltViewModel()
            FizzBuzzFormScreen(
                viewModel = viewModel,
                goToList = { firstInt, secondInt, firstWord, secondWord ->
                    navController.navigate(FizzBuzzNavigationItems.ListPage.buildRoute(firstInt, secondInt, firstWord, secondWord))
                }
            )
        }
        composable(
            route = FizzBuzzNavigationItems.ListPage.screenRoute,
            arguments = listOf(
                navArgument(FIRST_INT_ARG) { type = NavType.IntType },
                navArgument(SECOND_INT_ARG) { type = NavType.IntType },
                navArgument(FIRST_WORD_ARG) { type = NavType.StringType },
                navArgument(SECOND_WORD_ARG) { type = NavType.StringType }
            )
        ) {
            val viewModel: FizzBuzzListViewModel = hiltViewModel()
            FizzBuzzListScreen(viewModel = viewModel)
        }
    }
}
