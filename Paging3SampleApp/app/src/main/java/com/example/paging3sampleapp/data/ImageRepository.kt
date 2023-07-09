package com.example.paging3sampleapp.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.paging3sampleapp.data.remote.RemoteHelper
import com.example.paging3sampleapp.ui.model.ImageData
import com.example.paging3sampleapp.util.DATA_PER_PAGE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ImageRepository @Inject constructor(
    private val remoteHelper: RemoteHelper,
) {

    fun getImageListPaging(): Flow<PagingData<ImageData>> = Pager(
        config = PagingConfig(pageSize = DATA_PER_PAGE, enablePlaceholders = false),
        pagingSourceFactory = { ImagePagingSource(remoteHelper) },
        initialKey = 1
    ).flow
}