package com.android.paging3demo.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.android.paging3demo.model.UnsplashImage
import com.android.paging3demo.util.Constants.UNSPLASH_IMAGE_TABLE

@Dao
interface UnsplashImageDao {
    @Query(GET_ALL_IMAGES)
    fun getAllImages(): PagingSource<Int, UnsplashImage>

    @Insert(onConflict = REPLACE)
    suspend fun addImages(images: List<UnsplashImage>)

    @Query(DELETE_ALL_IMAGES)
    suspend fun deleteAllImages()

    companion object {
        const val GET_ALL_IMAGES = "SELECT * FROM $UNSPLASH_IMAGE_TABLE"
        const val DELETE_ALL_IMAGES = "DELETE FROM $UNSPLASH_IMAGE_TABLE"
    }
}