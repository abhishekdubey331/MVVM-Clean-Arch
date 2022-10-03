package com.sample.template.domain.repository

import com.google.common.truth.Truth.assertThat
import com.sample.template.api.BlogApi
import com.sample.template.domain.repository.contract.BlogContentRepository
import com.sample.template.domain.repository.impl.BlogContentRepositoryImpl
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
    fun `test fetch blog content success`() = runTest {
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
