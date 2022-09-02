package com.truecaller.assignment.domain.usecase.impl

import com.truecaller.assignment.common.Resource
import com.truecaller.assignment.domain.repository.contract.BlogContentRepository
import com.truecaller.assignment.domain.usecase.contract.GetNthCharUseCase
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class GetNthCharUseCaseImpl @Inject constructor(
    private val repository: BlogContentRepository
) : GetNthCharUseCase {

    override fun invoke(n: Int) = flow {
        try {
            emit(Resource.Loading())
            val blogContent = repository.fetchBlogContent()
            emit(Resource.Success(getNthChar(blogContent, n)))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Something went wrong"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }
    }

    private fun getNthChar(blogContent: String, n: Int) = blogContent[n]
}