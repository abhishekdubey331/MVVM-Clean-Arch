package com.truecaller.assignment.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.truecaller.assignment.databinding.ActivityBlogContentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BlogContentActivity : AppCompatActivity() {

    private val blogContentViewModel: BlogContentViewModel by viewModels()
    private lateinit var binding: ActivityBlogContentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBlogContentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonBlogScreenFetchContent.setOnClickListener {
            blogContentViewModel.getNthChar(10)
            blogContentViewModel.getEveryNthChar(10)
            blogContentViewModel.getWordCounter()
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                blogContentViewModel.state.collect { uiState ->
                    updateLoadingState(uiState.isLoading)
                    updateContent(uiState)
                }
            }
        }
    }

    private fun updateContent(uiState: BlogContentUiState) {
        binding.textviewBlogScreen10thCharContent.text = uiState.nthChar.toString()
        binding.textviewBlogScreenEvery10thCharContent.text = uiState.everyNthChar
        binding.textviewBlogScreenWordCounterContent.text = uiState.wordCount
        binding.textviewErrorBlogScreen.text = uiState.errorMessage
    }

    private fun updateLoadingState(loading: Boolean) {
        if (loading) {
            binding.progressBarBlogScreen.visibility = View.VISIBLE
        } else {
            binding.progressBarBlogScreen.visibility = View.GONE
        }
    }
}
