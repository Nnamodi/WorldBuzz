package com.roland.android.domain.usecase

import androidx.paging.PagingData
import com.roland.android.domain.model.Article
import com.roland.android.domain.model.LanguageModel
import com.roland.android.domain.model.Source
import com.roland.android.domain.repository.NewsRepository
import com.roland.android.domain.usecase.Collections.NewsBySource
import com.roland.android.domain.usecase.Collections.ReadingHistory
import com.roland.android.domain.usecase.Collections.SavedArticles
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNewsByCollectionUseCase(
	configuration: Configuration,
	private val newsRepository: NewsRepository // for easy mocking during unit test
) : UseCase<GetNewsByCollectionUseCase.Request, GetNewsByCollectionUseCase.Response>(configuration) { //, KoinComponent {

//	private val newsRepository by inject<NewsRepository>()

	override fun process(request: Request): Flow<Response> {
		return when (request.collection) {
			NewsBySource -> newsRepository.fetchNewsBySource(
				request.source.name,
				request.language.code
			)
			SavedArticles -> newsRepository.fetchSavedArticles()
			ReadingHistory -> newsRepository.fetchReadingHistory()
		}
			.map { Response(it) }
	}

	data class Request(
		val collection: Collections,
		val source: Source,
		val language: LanguageModel
	) : UseCase.Request

	data class Response(val articles: PagingData<Article>) : UseCase.Response

}

enum class Collections(val id: String) {
	NewsBySource("by_source"),
	SavedArticles("saved_news"),
	ReadingHistory("reading_history")
}