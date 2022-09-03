package com.truecaller.assignment.domain.usecase.impl

import com.google.common.truth.Truth.assertThat
import com.truecaller.assignment.common.UiState
import com.truecaller.assignment.domain.repository.contract.BlogContentRepository
import com.truecaller.assignment.domain.usecase.contract.GetEveryNthCharUseCase
import com.truecaller.assignment.ui.base.MainCoroutinesRule
import com.truecaller.assignment.utils.StringUtils
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
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
class GetEveryNthCharUseCaseTest {

    @Mock
    lateinit var blogContentRepository: BlogContentRepository

    @Mock
    lateinit var stringUtils: StringUtils

    @get:Rule
    var coroutineRule = MainCoroutinesRule()

    private lateinit var getEveryNthCharUseCase: GetEveryNthCharUseCase

    private val nthValue = 10

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        getEveryNthCharUseCase =
            GetEveryNthCharUseCaseImpl(
                blogContentRepository,
                stringUtils,
                coroutineRule.testDispatcher
            )
    }

    @Test
    fun `test every nth char`() = runTest {
        // Given
        val sampleResponse = "sample server response"
        // as index start from 0 so 9th char is the 10th char
        val expectedResult = "${sampleResponse[nthValue - 1]},${sampleResponse[nthValue * 2 - 1]}"
        // When
        whenever(blogContentRepository.fetchBlogContent()).thenReturn(sampleResponse)
        val result = getEveryNthCharUseCase.invoke(nthValue)

        // Then
        val allResult = result.toList()
        assertThat(allResult.first()).isInstanceOf(UiState.Loading::class.java)
        assertThat((allResult.last() as UiState.Success).data).isEqualTo(expectedResult)
        Mockito.verify(blogContentRepository, Mockito.times(1)).fetchBlogContent()
    }

    @Test
    fun `test every nth char api failure scenario`() = runTest {
        // Given
        val sampleErrorResponse = "Something Went Wrong!"
        val body = "Test Error Message".toResponseBody("text/html".toMediaTypeOrNull())
        val httpException = HttpException(Response.error<ResponseBody>(500, body))

        // When
        whenever(blogContentRepository.fetchBlogContent()).thenThrow(httpException)
        whenever(stringUtils.somethingWentWrong()).thenReturn(sampleErrorResponse)
        val result = getEveryNthCharUseCase.invoke(nthValue)

        // Then
        val allResult = result.toList()
        assertThat(allResult.first()).isInstanceOf(UiState.Loading::class.java)
        assertThat(allResult.last()).isInstanceOf(UiState.Failure::class.java)
        assertThat((allResult.last() as UiState.Failure).errorMessage).isEqualTo(sampleErrorResponse)
        Mockito.verify(blogContentRepository, Mockito.times(1)).fetchBlogContent()
    }
}
