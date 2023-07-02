package com.example.paging3sampleapp.ui.model

data class ImageData(
    val id: String, // 이미지 고유 id
    val author: String, // 이미지 주인
    val width: Int, // original image width
    val height: Int, // original image height
    val url: String, // original image url
    val download_url: String, // download image url
)