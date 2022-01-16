package com.android.paging3demo.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.paging3demo.model.UnsplashRemoteKeys
import com.android.paging3demo.util.Constants

@Dao
interface UnsplashRemoteKeysDao {
    @Query(GET_REMOTE_KEYS)
    suspend fun getRemoteKeys(id: String): UnsplashRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<UnsplashRemoteKeys>)

    @Query(DELETE_ALL_REMOTE_KEYS)
    suspend fun deleteAllRemoteKeys()

    companion object {
        const val GET_REMOTE_KEYS = "SELECT * FROM ${Constants.UNSPLASH_REMOTE_KEYS_TABLE} WHERE id = :id"
        const val DELETE_ALL_REMOTE_KEYS = "DELETE FROM ${Constants.UNSPLASH_REMOTE_KEYS_TABLE}"
    }
}