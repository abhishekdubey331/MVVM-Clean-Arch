package com.truecaller.assignment.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.truecaller.assignment.common.Resource
import com.truecaller.assignment.domain.usecase.contract.GetEveryNthCharUseCase
import com.truecaller.assignment.domain.usecase.contract.GetNthCharUseCase
import com.truecaller.assignment.domain.usecase.contract.GetWordCounterUseCase
import com.truecaller.assignment.ui.base.MainCoroutinesRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.Mockito.`when` as whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class BlogContentViewModelTest {

    private lateinit var blogContentViewModel: BlogContentViewModel

    @Mock
    lateinit var getNthCharUseCase: GetNthCharUseCase

    @Mock
    lateinit var getEveryNthCharUseCase: GetEveryNthCharUseCase

    @Mock
    lateinit var getWordCounterUseCase: GetWordCounterUseCase

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val dispatcherRule = MainCoroutinesRule()

    private val nthValue = 10

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        blogContentViewModel = BlogContentViewModel(
            getNthCharUseCase,
            getEveryNthCharUseCase,
            getWordCounterUseCase
        )
    }

    @Test
    fun `test nth char`() = runTest {
        // Given
        val result = 'K'
        val flow = flow {
            emit(Resource.Success(result))
        }

        // When
        whenever(getNthCharUseCase.invoke(nthValue)).thenReturn(flow)
        blogContentViewModel.getNthChar(nthValue)

        // Then
        assertThat(blogContentViewModel.state.value.nthChar).isEqualTo(result)
        assertThat(blogContentViewModel.state.value.everyNthChar).isEqualTo("")
        assertThat(blogContentViewModel.state.value.wordCount).isEqualTo("")
        assertThat(blogContentViewModel.state.value.isLoading).isFalse()
        assertThat(blogContentViewModel.state.value.message).isNull()
    }

    @Test
    fun `test every nth char`() = runTest {
        // Given
        val result = "test_string_with_every_nth_character"
        val flow = flow {
            emit(Resource.Success(result))
        }

        // When
        whenever(getEveryNthCharUseCase.invoke(nthValue)).thenReturn(flow)
        blogContentViewModel.getEveryNthChar(nthValue)

        // Then
        assertThat(blogContentViewModel.state.value.everyNthChar).isEqualTo(result)
        assertThat(blogContentViewModel.state.value.nthChar).isEqualTo(Char.MIN_VALUE)
        assertThat(blogContentViewModel.state.value.wordCount).isEqualTo("")
        assertThat(blogContentViewModel.state.value.isLoading).isFalse()
        assertThat(blogContentViewModel.state.value.message).isNull()
    }

    @Test
    fun `test word counter`() = runTest {
        // Given
        val result = "word_count"
        val flow = flow {
            emit(Resource.Success(result))
        }

        // When
        whenever(getWordCounterUseCase.invoke()).thenReturn(flow)
        blogContentViewModel.getWordCounter()

        // Then
        assertThat(blogContentViewModel.state.value.wordCount).isEqualTo(result)
        assertThat(blogContentViewModel.state.value.nthChar).isEqualTo(Char.MIN_VALUE)
        assertThat(blogContentViewModel.state.value.everyNthChar).isEqualTo("")
        assertThat(blogContentViewModel.state.value.isLoading).isFalse()
        assertThat(blogContentViewModel.state.value.message).isNull()
    }
}

