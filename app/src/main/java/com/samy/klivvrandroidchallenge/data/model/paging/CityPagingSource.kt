package com.samy.klivvrandroidchallenge.data.model.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.samy.klivvrandroidchallenge.data.model.City
import javax.inject.Inject

class CityPagingSource @Inject constructor(
    private val cities: List<City>,
) : PagingSource<Int, City>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, City> {
        return try {
            val currentPage = params.key ?: 1
            val pageSize = params.loadSize
            val start = (currentPage - 1) * pageSize
            val end = minOf(start + pageSize, cities.size)
            val page = cities.subList(start, end)
            LoadResult.Page(
                data = page,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (end >= cities.size) null else currentPage + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, City>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
