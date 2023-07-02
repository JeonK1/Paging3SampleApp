package com.example.paging3sampleapp.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paging3sampleapp.data.ImageRepository
import com.example.paging3sampleapp.ui.model.ImageData
import com.example.paging3sampleapp.util.NetworkStateManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val imageRepository: ImageRepository
) : ViewModel() {
    private val _imageList = MutableStateFlow<List<ImageData>>(listOf())
    val imageList: StateFlow<List<ImageData>> get() = _imageList

    private var currentPage = 0 // Network 에 요청할 Page 정보

    private suspend fun requestImageList(page: Int, networkState: NetworkStateManager.NetworkState) {
        _imageList.update {
            imageList.first() + imageRepository.getImageList(page, networkState)
        }
    }

    fun requestNextImageList(networkState: NetworkStateManager.NetworkState) =
        viewModelScope.launch {
            Log.e("test", "${networkState}")
            requestImageList(++currentPage, networkState)
        }
}