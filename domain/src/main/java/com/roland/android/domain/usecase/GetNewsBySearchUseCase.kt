package com.roland.android.domain.usecase

import androidx.paging.PagingData
import com.roland.android.domain.model.Article
import com.roland.android.domain.repository.NewsRepository
import com.roland.android.domain.util.Constant.SEPARATOR
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNewsBySearchUseCase(
	configuration: Configuration,
	private val newsRepository: NewsRepository // for easy mocking during unit test
) : UseCase<GetNewsBySearchUseCase.Request, GetNewsBySearchUseCase.Response>(configuration) {

	override fun process(request: Request): Flow<Response> {
		return newsRepository.searchNews(
			query = request.query,
			category = request.category,
			sources = request.sources.joinToString { SEPARATOR }
		)
			.map { Response(it) }
	}

	data class Request(
		val query: String,
		val category: String,
		val sources: List<String>
	) : UseCase.Request

	data class Response(val articles: PagingData<Article>) : UseCase.Response

}