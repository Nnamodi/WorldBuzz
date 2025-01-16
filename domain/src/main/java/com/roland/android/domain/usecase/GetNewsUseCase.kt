package com.roland.android.domain.usecase

import com.roland.android.domain.model.Article
import com.roland.android.domain.model.CategoryModel
import com.roland.android.domain.model.LanguageModel
import com.roland.android.domain.model.Source
import com.roland.android.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetNewsUseCase(
	configuration: Configuration,
	private val newsRepository: NewsRepository // for easy mocking during unit test
) : UseCase<GetNewsUseCase.Request, GetNewsUseCase.Response>(configuration) {

	override fun process(request: Request): Flow<Response> = combine(
		newsRepository.fetchTrendingNews(
			request.categoryModel.category,
			request.source.name,
			request.language.code
		),
		newsRepository.fetchRecommendedNews(
			request.categoryModel.category,
			request.language.code
		)
	) { trending, recommended ->
		Response(trending, recommended)
	}

	data class Request(
		val categoryModel: CategoryModel,
		val source: Source,
		val language: LanguageModel
	) : UseCase.Request

	data class Response(
		val trendingNews: List<Article>,
		val recommendedNews: List<Article>
	) : UseCase.Response

}