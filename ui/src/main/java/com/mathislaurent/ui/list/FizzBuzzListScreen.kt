package com.mathislaurent.ui.list

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.mathislaurent.ui.list.item.FizzBuzzItem
import com.mathislaurent.ui.theme.FizzBuzzTheme
import kotlinx.coroutines.flow.flowOf

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

@Preview(showSystemUi = true)
@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
    FizzBuzzTheme {
        val fakeList = flowOf(PagingData.from(listOf("hello", "world", "1"))).collectAsLazyPagingItems()
        FizzBuzzList(list = fakeList)
    }
}
