package com.mathislaurent.ui.list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems

@Composable
fun FizzBuzzListScreen(
    viewModel: FizzBuzzListViewModel
) {
    val list = viewModel.fizzBuzzListUiState.collectAsLazyPagingItems()
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        FizzBuzzList(
            list = list
        )
    }
}

@Composable
fun FizzBuzzList(list: LazyPagingItems<String>) {
    val stateList = rememberLazyListState()
    val listHeight = remember {
        mutableStateOf(0)
    }
    LazyColumn(
        modifier = Modifier.onGloballyPositioned {
            listHeight.value = it.size.height
        },
        state = stateList,
        contentPadding = PaddingValues(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(
                with(LocalDensity.current) {
                    (listHeight.value / 2).toDp()
                }
            ))
        }
        items(
            count = list.itemCount,
            key = { it }
        ) { i ->
            FizzBuzzItem(
                text = list[i].orEmpty()
            )
        }
    }
}

@Composable
fun FizzBuzzItem(
    text: String
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.width(200.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp,
                pressedElevation = 2.dp
            ),
            border = BorderStroke(1.dp, Color.DarkGray)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color.Cyan,
                                Color(0xFF3C4CAD),
                                Color(0xFF240E8B),
                                Color(0xFFF04393)
                            )
                        )
                    )
                    .padding(vertical = 38.dp),
                text = text,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }

}
