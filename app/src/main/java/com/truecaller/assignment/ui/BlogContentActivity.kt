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
        binding.fetchBlogContentBtn.setOnClickListener {
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

    private fun updateContent(uiState: BlogContentScreenState) {
        binding.resultFirstTv.text = uiState.nthChar.toString()
        binding.resultSecondTv.text = uiState.everyNthChar
        binding.resultThirdTv.text = uiState.wordCount
        binding.errorTextView.text = uiState.message
    }

    private fun updateLoadingState(loading: Boolean) {
        if (loading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}
