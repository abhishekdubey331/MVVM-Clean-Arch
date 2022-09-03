package com.truecaller.assignment.domain.repository

import com.google.common.truth.Truth.assertThat
import com.truecaller.assignment.api.BlogApi
import com.truecaller.assignment.domain.repository.contract.BlogContentRepository
import com.truecaller.assignment.domain.repository.impl.BlogContentRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.Mockito.`when` as whenever

@RunWith(JUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class BlogContentRepositoryTest {

    private lateinit var blogContentRepository: BlogContentRepository

    @Mock
    lateinit var blogApi: BlogApi

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        blogContentRepository = BlogContentRepositoryImpl(blogApi)
    }


    @Test
    fun `fetch blog content test`() = runTest {
        // Given
        val response = "blog content"

        // When
        whenever(blogApi.fetchBlogContent()).thenReturn(response)
        val result = blogContentRepository.fetchBlogContent()

        // Then
        assertThat(result).isEqualTo(response)
        Mockito.verify(blogApi, Mockito.times(1)).fetchBlogContent()
    }
}

