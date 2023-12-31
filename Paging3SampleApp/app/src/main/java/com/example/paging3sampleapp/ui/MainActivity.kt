package com.example.paging3sampleapp.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.example.paging3sampleapp.databinding.ActivityMainBinding
import com.example.paging3sampleapp.util.NetworkStateManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainAdapter = MainAdapter()
    private val viewModel: MainViewModel by viewModels()

    private var coroutineJob: Job? = null // 중복 방지 체크 용도의 Job
    private val networkStateManager = NetworkStateManager(this)
    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (binding.rvRecyclerView.canScrollVertically(1).not() && coroutineJob == null) {
                // end of scroll
                coroutineJob = CoroutineScope(Dispatchers.Main + SupervisorJob()).launch {
                    viewModel.requestNextImageList(networkStateManager.networkState.first())
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        networkStateManager.register()

        binding.rvRecyclerView.apply {
            adapter = mainAdapter
            addOnScrollListener(scrollListener)
        }

        lifecycleScope.launch {
            viewModel.requestNextImageList(networkStateManager.networkState.first())

            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.imageList.collectLatest {
                    mainAdapter.submitList(it)
                    coroutineJob = null
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.rvRecyclerView.removeOnScrollListener(scrollListener)
        networkStateManager.unregister()
        coroutineJob?.cancel()
        coroutineJob = null
    }
}