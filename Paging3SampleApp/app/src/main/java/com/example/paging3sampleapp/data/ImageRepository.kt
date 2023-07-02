package com.example.paging3sampleapp.data

import com.example.paging3sampleapp.data.local.LocalDao
import com.example.paging3sampleapp.data.remote.RemoteHelper
import com.example.paging3sampleapp.ui.model.ImageData
import com.example.paging3sampleapp.util.DATA_PER_PAGE
import com.example.paging3sampleapp.util.NetworkStateManager
import javax.inject.Inject

class ImageRepository @Inject constructor(
    private val remoteHelper: RemoteHelper,
    private val localDao: LocalDao
) {
    suspend fun getImageList(
        page: Int,
        networkState: NetworkStateManager.NetworkState
    ): List<ImageData> = when (networkState) {
        NetworkStateManager.NetworkState.Available -> {
            // get data from network
            val remoteImageList = remoteHelper.getImageList(page, DATA_PER_PAGE)

            val pageSize = localDao.getPageSize()
            if (page > pageSize) {
                // insert data to database
                localDao.insertAll(
                    remoteImageList.mapIndexed { idx, imageData ->
                        imageData.toImageLocalData((DATA_PER_PAGE * (page - 1) + idx + 1))
                    }
                )
            } else {
                // update data to database
                localDao.updateAll(
                    remoteImageList.mapIndexed { idx, imageData ->
                        imageData.toImageLocalData((DATA_PER_PAGE * (page - 1) + idx + 1))
                    }
                )
            }

            // return data
            remoteImageList.map { it.toImageData() }
        }

        NetworkStateManager.NetworkState.Unavailable -> {
            // get data from database
            val localImageList = localDao.getImageList(page - 1, DATA_PER_PAGE)

            // return data
            localImageList.map { it.toImageData() }
        }
    }
}