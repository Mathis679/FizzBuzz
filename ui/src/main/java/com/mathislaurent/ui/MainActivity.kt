package com.mathislaurent.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.mathislaurent.ui.navigation.FizzBuzzNavigationGraph
import com.mathislaurent.ui.theme.FizzBuzzTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FizzBuzzTheme {
                FizzBuzzNavigationGraph(navController = rememberNavController())
            }
        }
    }
}
