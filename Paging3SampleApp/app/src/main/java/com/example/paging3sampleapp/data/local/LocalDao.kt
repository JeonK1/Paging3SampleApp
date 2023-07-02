package com.example.paging3sampleapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface LocalDao {
    @Insert
    suspend fun insertAll(imageDataList: List<ImageLocalData>)

    @Update
    suspend fun updateAll(imageDataList: List<ImageLocalData>)

    @Query("SELECT * FROM image_table WHERE imageId >= (:page * :limit) + 1 LIMIT :limit")
    suspend fun getImageList(page: Int, limit: Int): List<ImageLocalData>

    @Query("SELECT ((COUNT(*) - 1) / 10) + 1 FROM image_table")
    suspend fun getPageSize(): Int
}