package com.example.paging3sampleapp.data.remote

import javax.inject.Inject

interface RemoteHelper {
    /*** image list 가져오기 ***/
    suspend fun getImageList(page: Int, limit: Int): List<ImageRemoteData>
}

class RemoteHelperImpl @Inject constructor(
    private val remoteService: RemoteService
) : RemoteHelper {
    override suspend fun getImageList(page: Int, limit: Int): List<ImageRemoteData> =
        remoteService.getImageList(page, limit)
}