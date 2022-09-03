package com.truecaller.assignment.domain.usecase.impl

import com.truecaller.assignment.common.UiState
import com.truecaller.assignment.di.IoDispatcher
import com.truecaller.assignment.domain.repository.contract.BlogContentRepository
import com.truecaller.assignment.domain.usecase.contract.GetWordCounterUseCase
import com.truecaller.assignment.utils.StringUtils
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetWordCounterUseCaseImpl @Inject constructor(
    private val repository: BlogContentRepository,
    private val stringUtils: StringUtils,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : GetWordCounterUseCase {

    override fun invoke() = flow {
        try {
            emit(UiState.Loading)
            val blogContent = repository.fetchBlogContent()
            val wordCountMap = getWordCountMapFromBlogContent(blogContent)
            emit(UiState.Success(getWordCountStringFromMap(wordCountMap)))
        } catch (e: HttpException) {
            emit(UiState.Failure(stringUtils.somethingWentWrong()))
        } catch (e: IOException) {
            emit(UiState.Failure(stringUtils.noNetworkErrorMessage()))
        }
    }.flowOn(ioDispatcher)

    private fun getWordCountMapFromBlogContent(blogContent: String) = blogContent
        .split("\\s+".toRegex())
        .groupingBy { it }
        .eachCount()

    private fun getWordCountStringFromMap(wordCountMap: Map<String, Int>) = buildString {
        wordCountMap.map {
            append("${it.key}: ${it.value}")
            append("\n")
        }
    }
}
