package com.mathislaurent.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mathislaurent.core.di.modules.MainDispatcher
import com.mathislaurent.core.provider.FizzBuzzProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FizzBuzzPagingSource @Inject constructor(
    private val provider: FizzBuzzProvider,
    @MainDispatcher private val dispatcher: CoroutineDispatcher
): PagingSource<Int, String>() {

    private var _firstInt: Int = 0
    private var _secondInt: Int = 0
    private var _firstWord: String = ""
    private var _secondWord: String = ""

    fun setArguments(
        firstInt: Int,
        secondInt: Int,
        firstWord: String,
        secondWord: String
    ) {
        _firstInt = firstInt
        _secondInt = secondInt
        _firstWord = firstWord
        _secondWord = secondWord
    }

    override fun getRefreshKey(state: PagingState<Int, String>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, String> = withContext(dispatcher) {
        val page = params.key ?: 0
        val pageSize = params.loadSize
        val currentSize = page * pageSize
        val range = IntRange(currentSize, currentSize + pageSize)

        val result = provider(_firstInt, _secondInt, _firstWord, _secondWord, range)
        LoadResult.Page(
            data = result,
            prevKey = if (page == 0) null else page.minus(1),
            nextKey = if (result.isEmpty()) null else page.plus(1)
        )
    }

}
