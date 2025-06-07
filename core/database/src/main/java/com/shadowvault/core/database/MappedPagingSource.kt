package com.shadowvault.core.database

import androidx.paging.PagingSource
import androidx.paging.PagingState

class MappedPagingSource<T : Any, R : Any>(
    private val source: PagingSource<Int, T>,
    private val mapper: (T) -> R
) : PagingSource<Int, R>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, R> {
        val result = source.load(params)
        return when (result) {
            is LoadResult.Page -> {
                val mappedData = result.data.map { mapper(it) }
                LoadResult.Page(
                    data = mappedData,
                    prevKey = result.prevKey,
                    nextKey = result.nextKey,
                    itemsBefore = result.itemsBefore,
                    itemsAfter = result.itemsAfter
                )
            }
            is LoadResult.Error -> LoadResult.Error(result.throwable)
            is LoadResult.Invalid -> LoadResult.Invalid()
        }
    }

    override fun getRefreshKey(state: PagingState<Int, R>): Int? {
        @Suppress("UNCHECKED_CAST")
        return (source as PagingSource<Int, T>).getRefreshKey(state as PagingState<Int, T>)
    }
}
