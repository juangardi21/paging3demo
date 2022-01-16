package com.android.paging3demo.data.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.android.paging3demo.data.local.UnsplashDataBase
import com.android.paging3demo.data.remote.UnsplashApi
import com.android.paging3demo.model.UnsplashImage
import com.android.paging3demo.model.UnsplashRemoteKeys
import com.android.paging3demo.util.Constants.PAGE_SIZE

@ExperimentalPagingApi
class UnsplashRemoteMediator(
    private val unsplashApi: UnsplashApi,
    private val unsplashDataBase: UnsplashDataBase
) : RemoteMediator<Int, UnsplashImage>() {

    private val unsplashImageDao = unsplashDataBase.unsplashImageDao()
    private val unsplashRemoteKeysDao = unsplashDataBase.unsplashRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UnsplashImage>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                    nextPage
                }
            }

            val response = unsplashApi.getAllImages(page = currentPage, perPage = PAGE_SIZE)
            val endOfPaginationReached = response.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            unsplashDataBase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    unsplashImageDao.deleteAllImages()
                    unsplashRemoteKeysDao.deleteAllRemoteKeys()
                    Log.d("RemoteMediator", "REFRESH")
                }

                Log.d("RemoteMediator", " 2222")
                val keys = response.map { unsplashImage ->
                    UnsplashRemoteKeys(
                        id = unsplashImage.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }

                unsplashRemoteKeysDao.addAllRemoteKeys(remoteKeys = keys)
                unsplashImageDao.addImages(images = response)
            }
            MediatorResult.Success(
                endOfPaginationReached = endOfPaginationReached
            )
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, UnsplashImage>): UnsplashRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                unsplashRemoteKeysDao.getRemoteKeys(id = id)
            }
        }
    }

    private fun getRemoteKeyForFirstItem(state: PagingState<Int, UnsplashImage>): UnsplashRemoteKeys? {
        return state.pages.firstOrNull() { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { unsplashImage ->
                unsplashRemoteKeysDao.getRemoteKeys(id = unsplashImage.id)
            }
    }

    private fun getRemoteKeyForLastItem(state: PagingState<Int, UnsplashImage>): UnsplashRemoteKeys? {
        return state.pages.lastOrNull() { it.data.isEmpty() }?.data?.lastOrNull()
            ?.let { unsplashImage ->
                unsplashRemoteKeysDao.getRemoteKeys(id = unsplashImage.id)
            }
    }
}