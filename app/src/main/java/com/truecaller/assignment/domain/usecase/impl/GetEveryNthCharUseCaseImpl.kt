package com.truecaller.assignment.domain.usecase.impl

import com.truecaller.assignment.common.UiState
import com.truecaller.assignment.di.IoDispatcher
import com.truecaller.assignment.domain.repository.contract.BlogContentRepository
import com.truecaller.assignment.domain.usecase.contract.GetEveryNthCharUseCase
import com.truecaller.assignment.utils.StringUtils
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetEveryNthCharUseCaseImpl @Inject constructor(
    private val repository: BlogContentRepository,
    private val stringUtils: StringUtils,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : GetEveryNthCharUseCase {

    override fun invoke(n: Int) = flow {
        try {
            emit(UiState.Loading)
            val blogContent = repository.fetchBlogContent()
            emit(UiState.Success(getEveryNthCharFromBlogContent(blogContent, n)))
        } catch (e: HttpException) {
            emit(UiState.Failure(stringUtils.somethingWentWrong()))
        } catch (e: IOException) {
            emit(UiState.Failure(stringUtils.noNetworkErrorMessage()))
        }
    }.flowOn(ioDispatcher)

    private fun getEveryNthCharFromBlogContent(blogContent: String, n: Int) = buildString {
        for (i in n.minus(1)..blogContent.length step n) {
            append(blogContent[i].plus(","))
        }
        deleteCharAt(this.length.minus(1))
    }
}
