package com.example.paging3sampleapp.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.paging3sampleapp.data.remote.RemoteHelper
import com.example.paging3sampleapp.ui.model.ImageData
import com.example.paging3sampleapp.util.DATA_PER_PAGE
import java.lang.Exception

class ImagePagingSource(
    private val remoteHelper: RemoteHelper
): PagingSource<Int, ImageData>() {

    private val INITIAL_PAGE_NUBMER = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImageData> = try {
        val nextPageNumber = params.key ?: INITIAL_PAGE_NUBMER
        val response = remoteHelper.getImageList(nextPageNumber, DATA_PER_PAGE)
        LoadResult.Page(
            data = response.map { it.toImageData() },
            prevKey = if (nextPageNumber - 1 >= INITIAL_PAGE_NUBMER) nextPageNumber -1 else null,
            nextKey = nextPageNumber + 1
        )
    } catch (e: Exception) {
        // Exception 처리 코드
        LoadResult.Error(e)
    }

    override fun getRefreshKey(state: PagingState<Int, ImageData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}