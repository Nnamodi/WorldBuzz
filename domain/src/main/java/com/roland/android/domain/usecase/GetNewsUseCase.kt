package com.roland.android.domain.usecase

import com.roland.android.domain.model.Article
import com.roland.android.domain.model.CategoryModel
import com.roland.android.domain.model.Source
import com.roland.android.domain.repository.NewsRepository
import com.roland.android.domain.util.Constant.SEPARATOR
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetNewsUseCase(
	configuration: Configuration,
	private val newsRepository: NewsRepository // for easy mocking during unit test
) : UseCase<GetNewsUseCase.Request, GetNewsUseCase.Response>(configuration) {

	override fun process(request: Request): Flow<Response> = combine(
		newsRepository.fetchTrendingNews(
			request.categoryModels.joinToString(SEPARATOR) { it.category },
			request.sources.joinToString(SEPARATOR) { it.name },
			request.languageCode
		),
		newsRepository.fetchRecommendedNews(
			request.categoryModels.getOrNull(0)?.category ?: "",
			request.languageCode
		)
	) { trending, recommended ->
		Response(trending, recommended)
	}

	data class Request(
		val categoryModels: List<CategoryModel>,
		val sources: List<Source>,
		val languageCode: String
	) : UseCase.Request

	data class Response(
		val trendingNews: List<Article>,
		val recommendedNews: List<Article>
	) : UseCase.Response

}