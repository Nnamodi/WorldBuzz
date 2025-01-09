package com.roland.android.domain.usecase

import androidx.paging.PagingData
import com.roland.android.domain.model.Article
import com.roland.android.domain.model.CategoryModel
import com.roland.android.domain.model.LanguageModel
import com.roland.android.domain.model.Source
import com.roland.android.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetNewsUseCase(
	configuration: Configuration
) : UseCase<GetNewsUseCase.Request, GetNewsUseCase.Response>(configuration), KoinComponent {

	private val newsRepository by inject<NewsRepository>()

	override fun process(request: Request): Flow<Response> = combine(
		newsRepository.fetchTrendingNews(
			request.categoryModel.category,
			request.source.name,
			request.language.code
		),
		newsRepository.fetchNewsByCategory(
			request.source.name,
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
		val trendingNews: PagingData<Article>,
		val recommendedNews: PagingData<Article>,
	) : UseCase.Response

}