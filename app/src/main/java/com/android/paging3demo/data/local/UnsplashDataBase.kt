package com.android.paging3demo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.paging3demo.data.local.dao.UnsplashImageDao
import com.android.paging3demo.data.local.dao.UnsplashRemoteKeysDao
import com.android.paging3demo.model.UnsplashImage
import com.android.paging3demo.model.UnsplashRemoteKeys

@Database(entities = [UnsplashImage::class, UnsplashRemoteKeys::class], version = 1)
abstract class UnsplashDataBase: RoomDatabase() {
    abstract fun unsplashImageDao(): UnsplashImageDao
    abstract fun unsplashRemoteKeysDao(): UnsplashRemoteKeysDao
}