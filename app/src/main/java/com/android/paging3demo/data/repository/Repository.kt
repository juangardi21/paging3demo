package com.android.paging3demo.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.android.paging3demo.data.local.UnsplashDataBase
import com.android.paging3demo.data.paging.SearchPagingSource
import com.android.paging3demo.data.paging.UnsplashRemoteMediator
import com.android.paging3demo.data.remote.UnsplashApi
import com.android.paging3demo.model.UnsplashImage
import com.android.paging3demo.util.Constants.PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class Repository @Inject constructor(
    private val unsplashApi: UnsplashApi,
    private val unsplashDataBase: UnsplashDataBase
) {

    fun getAllImages(): Flow<PagingData<UnsplashImage>> {
        val pagingSourceFactory = {
            unsplashDataBase.unsplashImageDao().getAllImages()
        }
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            remoteMediator = UnsplashRemoteMediator(
                unsplashApi = unsplashApi,
                unsplashDataBase = unsplashDataBase
            ),
            pagingSourceFactory = pagingSourceFactory,
        ).flow
    }

    fun searchImages(query: String): Flow<PagingData<UnsplashImage>> {
        val pagingSource = { SearchPagingSource(unsplashApi = unsplashApi, query = query) }
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = pagingSource,
        ).flow
    }

}