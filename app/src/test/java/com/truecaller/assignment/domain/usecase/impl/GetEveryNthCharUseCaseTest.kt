package com.truecaller.assignment.domain.usecase.impl

import com.google.common.truth.Truth.assertThat
import com.truecaller.assignment.common.Resource
import com.truecaller.assignment.domain.repository.contract.BlogContentRepository
import com.truecaller.assignment.domain.usecase.contract.GetEveryNthCharUseCase
import com.truecaller.assignment.ui.base.MainCoroutinesRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.Mockito.`when` as whenever


@OptIn(ExperimentalCoroutinesApi::class)
class GetEveryNthCharUseCaseTest {

    @Mock
    lateinit var blogContentRepository: BlogContentRepository

    @get:Rule
    var coroutineRule = MainCoroutinesRule()

    private lateinit var getEveryNthCharUseCase: GetEveryNthCharUseCase

    private val nthValue = 10

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        getEveryNthCharUseCase =
            GetEveryNthCharUseCaseImpl(blogContentRepository, coroutineRule.testDispatcher)
    }

    @Test
    fun `test every nth char`() = runTest {
        // Given
        val sampleResponse = "sample server response"
        // as index start from 0 so 9th char is the 10th char
        val expectedResult = "${sampleResponse[nthValue - 1]},${sampleResponse[nthValue * 2 - 1]},"
        // When
        whenever(blogContentRepository.fetchBlogContent()).thenReturn(sampleResponse)
        val result = getEveryNthCharUseCase.invoke(nthValue)

        // Then
        val allResult = result.toList()
        assertThat(allResult.first()).isInstanceOf(Resource.Loading::class.java)
        assertThat(allResult.last().data).isEqualTo(expectedResult)
        Mockito.verify(blogContentRepository, Mockito.times(1)).fetchBlogContent()
    }
}