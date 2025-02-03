package com.roland.android.domain.usecase

import androidx.paging.PagingData
import com.roland.android.domain.model.Article
import com.roland.android.domain.repository.NewsRepository
import com.roland.android.domain.usecase.Collections.NewsByCategory
import com.roland.android.domain.usecase.Collections.NewsBySource
import com.roland.android.domain.usecase.Collections.ReadingHistory
import com.roland.android.domain.usecase.Collections.SavedArticles
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNewsByCollectionUseCase(
	configuration: Configuration,
	private val newsRepository: NewsRepository // for easy mocking during unit test
) : UseCase<GetNewsByCollectionUseCase.Request, GetNewsByCollectionUseCase.Response>(configuration) {

	override fun process(request: Request): Flow<Response> {
		return when (request.collection) {
			NewsByCategory -> newsRepository.fetchNewsByCategory(
				request.category,
				request.languageCode
			)
			NewsBySource -> newsRepository.fetchNewsBySource(
				request.sourceName,
				request.languageCode
			)
			SavedArticles -> newsRepository.fetchSavedArticles()
			ReadingHistory -> newsRepository.fetchReadingHistory()
		}
			.map { Response(it) }
	}

	data class Request(
		val collection: Collections,
		val category: String = "",
		val sourceName: String = "",
		val languageCode: String
	) : UseCase.Request

	data class Response(val articles: PagingData<Article>) : UseCase.Response

}

enum class Collections(val id: String) {
	NewsByCategory("by_category"),
	NewsBySource("by_source"),
	SavedArticles("saved_news"),
	ReadingHistory("reading_history")
}