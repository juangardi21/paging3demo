package com.android.paging3demo.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.android.paging3demo.data.remote.UnsplashApi
import com.android.paging3demo.model.UnsplashImage
import com.android.paging3demo.util.Constants.PAGE_SIZE
import java.lang.Exception

class SearchPagingSource(
    private val unsplashApi: UnsplashApi,
    private val query: String,
) : PagingSource<Int, UnsplashImage>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashImage> {
        val currentPage = params.key ?: 1
        return try {
            val response = unsplashApi.searchImages(query = query, perPage = PAGE_SIZE)
            val endOfPaginationReached = response.images.isEmpty()

            if (response.images.isNotEmpty()) {
                LoadResult.Page(
                    data =  response.images,
                    prevKey = if (currentPage > 1) currentPage - 1 else null,
                    nextKey = if (endOfPaginationReached) null else currentPage + 1,
                )
            } else {
                LoadResult.Page(
                    data =  emptyList(),
                    prevKey = null,
                    nextKey = null,
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UnsplashImage>): Int? {
        return state.anchorPosition
    }
}