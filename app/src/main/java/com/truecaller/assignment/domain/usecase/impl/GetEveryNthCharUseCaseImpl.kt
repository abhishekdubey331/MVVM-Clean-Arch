package com.truecaller.assignment.domain.usecase.impl

import com.truecaller.assignment.common.Resource
import com.truecaller.assignment.di.IoDispatcher
import com.truecaller.assignment.domain.repository.contract.BlogContentRepository
import com.truecaller.assignment.domain.usecase.contract.GetEveryNthCharUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetEveryNthCharUseCaseImpl @Inject constructor(
    private val repository: BlogContentRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : GetEveryNthCharUseCase {

    override fun invoke(n: Int) = flow {
        try {
            emit(Resource.Loading())
            val blogContent = repository.fetchBlogContent()
            emit(Resource.Success(getEveryNthCharFromBlogContent(blogContent, n)))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Something went wrong"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }
    }.flowOn(ioDispatcher)

    private fun getEveryNthCharFromBlogContent(blogContent: String, n: Int) = buildString {
        for (i in n - 1..blogContent.length step n) {
            append(blogContent[i] + ",")
        }
        this.deleteCharAt(this.length - 1);
    }
}