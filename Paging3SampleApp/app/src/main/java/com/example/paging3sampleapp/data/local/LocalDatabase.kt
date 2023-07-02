package com.example.paging3sampleapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ImageLocalData::class], version = 1)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun roomDao(): LocalDao
}