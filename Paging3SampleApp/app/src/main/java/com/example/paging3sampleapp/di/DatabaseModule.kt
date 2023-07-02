package com.example.paging3sampleapp.di

import android.app.Application
import androidx.room.Room
import com.example.paging3sampleapp.data.local.LocalDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(app: Application) =
        Room.databaseBuilder(app, LocalDatabase::class.java, "image_database")
            .build()

    @Provides
    @Singleton
    fun provideImageLikeDao(db: LocalDatabase) = db.roomDao()
}