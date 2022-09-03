package com.truecaller.assignment.domain.usecase.impl

import com.google.common.truth.Truth.assertThat
import com.truecaller.assignment.common.Resource
import com.truecaller.assignment.domain.repository.contract.BlogContentRepository
import com.truecaller.assignment.domain.usecase.contract.GetNthCharUseCase
import com.truecaller.assignment.ui.base.MainCoroutinesRule
import com.truecaller.assignment.utils.StringUtils
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
class GetNthCharUseCaseTest {

    @get:Rule
    var coroutineRule = MainCoroutinesRule()

    @Mock
    lateinit var blogContentRepository: BlogContentRepository

    @Mock
    lateinit var stringUtils: StringUtils

    private lateinit var getNthCharUseCase: GetNthCharUseCase

    private val nthValue = 10

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        getNthCharUseCase = GetNthCharUseCaseImpl(
            blogContentRepository,
            stringUtils,
            coroutineRule.testDispatcher
        )
    }

    @Test
    fun `test nth char success scenario`() = runTest {
        // Given
        val sampleResponse = "sample server response"

        // When
        whenever(blogContentRepository.fetchBlogContent()).thenReturn(sampleResponse)
        val result = getNthCharUseCase.invoke(nthValue)

        // Then
        val allResult = result.toList()
        assertThat(allResult.first()).isInstanceOf(Resource.Loading::class.java)
        assertThat(allResult.last().data).isEqualTo(sampleResponse[nthValue - 1].toString())
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
        whenever(stringUtils.somethingWentWrong()).thenReturn(sampleErrorResponse)
        val result = getNthCharUseCase.invoke(nthValue)

        // Then
        val allResult = result.toList()
        println(allResult)
        assertThat(allResult.first()).isInstanceOf(Resource.Loading::class.java)
        assertThat(allResult.last().message).isEqualTo(sampleErrorResponse)
        Mockito.verify(blogContentRepository, Mockito.times(1)).fetchBlogContent()
    }
}
