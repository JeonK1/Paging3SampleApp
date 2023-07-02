package com.example.paging3sampleapp.data.remote

import com.example.paging3sampleapp.data.local.ImageLocalData
import com.example.paging3sampleapp.ui.model.ImageData

data class ImageRemoteData(
    val id: String, // 이미지 고유 id
    val author: String, // 이미지 주인
    val width: Int, // original image width
    val height: Int, // original image height
    val url: String, // original image url
    val download_url: String, // download image url
) {
    fun toImageData() =
        ImageData(id, author, width, height, url, download_url)

    fun toImageLocalData(imageId: Int = 0): ImageLocalData =
        ImageLocalData(imageId, id, author, width, height, url, download_url)
}