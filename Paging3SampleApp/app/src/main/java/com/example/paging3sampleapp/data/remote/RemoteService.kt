package com.example.paging3sampleapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteService {
    /***
     * List Images
     * @param page : 가져올 이미지 리스트의 page
     * @param limit : 한 page 에 포함된 최대 개수
     * ***/
    @GET("/v2/list")
    suspend fun getImageList(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): List<ImageRemoteData>
}