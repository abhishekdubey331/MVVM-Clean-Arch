package com.truecaller.assignment.domain.usecase.impl

import com.google.common.truth.Truth.assertThat
import com.truecaller.assignment.common.Resource
import com.truecaller.assignment.domain.repository.contract.BlogContentRepository
import com.truecaller.assignment.domain.usecase.contract.GetWordCounterUseCase
import com.truecaller.assignment.ui.base.MainCoroutinesRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.HttpException
import retrofit2.Response
import org.mockito.Mockito.`when` as whenever

@OptIn(ExperimentalCoroutinesApi::class)
class GetWordCounterUseCaseTest {

    @Mock
    lateinit var blogContentRepository: BlogContentRepository

    private lateinit var getWordCounterUseCase: GetWordCounterUseCase

    @get:Rule
    var coroutineRule = MainCoroutinesRule()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        getWordCounterUseCase =
            GetWordCounterUseCaseImpl(blogContentRepository, coroutineRule.testDispatcher)
    }

    @Test
    fun `test nth char success scenario`() = runTest {
        // Given
        val sampleResponse = "sample sample sample"
        val expectedResult = "sample: 3"

        // When
        whenever(blogContentRepository.fetchBlogContent()).thenReturn(sampleResponse)
        val result = getWordCounterUseCase.invoke()

        // Then
        val allResult = result.toList()
        assertThat(allResult.first()).isInstanceOf(Resource.Loading::class.java)
        assertThat(allResult.last().data?.trim()).isEqualTo(expectedResult.trim())
        Mockito.verify(blogContentRepository, Mockito.times(1)).fetchBlogContent()
    }

    @Test
    fun `test nth char api failure scenario`() = runTest {
        // Given
        val sampleErrorResponse = "HTTP 400 Response.error()"

        // When
        val httpException = HttpException(
            Response.error<ResponseBody>(
                400,
                ResponseBody.create(MediaType.parse("plain/text"), "")
            )
        )
        whenever(blogContentRepository.fetchBlogContent()).thenThrow(httpException)
        val result = getWordCounterUseCase.invoke()

        // Then
        val allResult = result.toList()
        assertThat(allResult.first()).isInstanceOf(Resource.Loading::class.java)
        assertThat(allResult.last().message).isEqualTo(sampleErrorResponse)
        Mockito.verify(blogContentRepository, Mockito.times(1)).fetchBlogContent()
    }
}
