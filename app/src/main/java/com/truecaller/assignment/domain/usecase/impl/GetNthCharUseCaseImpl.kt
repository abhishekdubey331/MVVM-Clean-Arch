package com.truecaller.assignment.domain.usecase.impl

import com.truecaller.assignment.common.Resource
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
            emit(Resource.Loading())
            val blogContent = repository.fetchBlogContent()
            val nThChar = getNthCharFromContent(blogContent, n)
            emit(Resource.Success(nThChar))
        } catch (e: HttpException) {
            emit(Resource.Error(stringUtils.somethingWentWrong()))
        } catch (e: IOException) {
            emit(Resource.Error(stringUtils.noNetworkErrorMessage()))
        }
    }.flowOn(ioDispatcher)

    private fun getNthCharFromContent(blogContent: String, n: Int) = blogContent[n - 1]
}