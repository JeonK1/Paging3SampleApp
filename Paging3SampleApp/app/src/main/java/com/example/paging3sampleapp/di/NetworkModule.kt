package com.example.paging3sampleapp.di

import com.example.paging3sampleapp.data.remote.RemoteHelper
import com.example.paging3sampleapp.data.remote.RemoteHelperImpl
import com.example.paging3sampleapp.data.remote.RemoteService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .baseUrl("https://picsum.photos")
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): RemoteService =
        retrofit.create(RemoteService::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: RemoteHelperImpl): RemoteHelper = apiHelper
}