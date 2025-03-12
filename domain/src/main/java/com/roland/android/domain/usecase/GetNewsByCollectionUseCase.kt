package com.roland.android.domain.usecase

import androidx.paging.PagingData
import com.roland.android.domain.model.Article
import com.roland.android.domain.model.CategoryModel
import com.roland.android.domain.model.Source
import com.roland.android.domain.repository.NewsRepository
import com.roland.android.domain.usecase.Collections.NewsByCategory
import com.roland.android.domain.usecase.Collections.NewsBySource
import com.roland.android.domain.usecase.Collections.ReadingHistory
import com.roland.android.domain.usecase.Collections.RecommendedNews
import com.roland.android.domain.usecase.Collections.SavedArticles
import com.roland.android.domain.usecase.Collections.TrendingNews
import com.roland.android.domain.util.Constant.SEPARATOR
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNewsByCollectionUseCase(
	configuration: Configuration,
	private val newsRepository: NewsRepository // for easy mocking during unit test
) : UseCase<GetNewsByCollectionUseCase.Request, GetNewsByCollectionUseCase.Response>(configuration) {

	override fun process(request: Request): Flow<Response> {
		return when (request.collection) {
			TrendingNews -> newsRepository.searchNews(
				query = "",
				category = request.categoryModels.getOrNull(0)?.category ?: "",
				sources = request.sources.joinToString(SEPARATOR) { it.name }
			)
			RecommendedNews -> newsRepository.fetchNewsByCategory(
				category = request.categoryModels.getOrNull(0)?.category ?: "",
				languageCode = request.languageCode
			)
			NewsByCategory -> newsRepository.fetchNewsByCategory(
				category = request.category,
				languageCode = request.languageCode
			)
			NewsBySource -> newsRepository.fetchNewsBySource(
				source = request.sourceName,
				languageCode = request.languageCode
			)
			SavedArticles -> newsRepository.fetchSavedArticles()
			ReadingHistory -> newsRepository.fetchReadingHistory()
		}
			.map { Response(it) }
	}

	data class Request(
		val collection: Collections,
		val categoryModels: List<CategoryModel> = emptyList(), // list of subscribed categories
		val sources: List<Source> = emptyList(), // list of subscribed sources
		val category: String = "",
		val sourceName: String = "",
		val languageCode: String
	) : UseCase.Request

	data class Response(val articles: PagingData<Article>) : UseCase.Response

}

enum class Collections {
	TrendingNews,
	RecommendedNews,
	NewsByCategory,
	NewsBySource,
	SavedArticles,
	ReadingHistory
}