package com.truecaller.assignment.domain.usecase.impl

import com.truecaller.assignment.common.UiState
import com.truecaller.assignment.di.IoDispatcher
import com.truecaller.assignment.domain.repository.contract.BlogContentRepository
import com.truecaller.assignment.domain.usecase.contract.GetNthCharUseCase
import com.truecaller.assignment.utils.StringUtils
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class GetNthCharUseCaseImpl @Inject constructor(
    private val repository: BlogContentRepository,
    private val stringUtils: StringUtils,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : GetNthCharUseCase {

    override fun invoke(n: Int) = flow {
        try {
            emit(UiState.Loading)
            val blogContent = repository.fetchBlogContent()
            val nThChar = getNthCharFromContent(blogContent, n)
            emit(UiState.Success(nThChar))
        } catch (e: HttpException) {
            emit(UiState.Failure(stringUtils.somethingWentWrong()))
        } catch (e: IOException) {
            emit(UiState.Failure(stringUtils.noNetworkErrorMessage()))
        }
    }.flowOn(ioDispatcher)

    private fun getNthCharFromContent(blogContent: String, n: Int) = blogContent[n - 1].toString()
}