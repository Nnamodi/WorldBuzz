package com.roland.android.data_remote.data_source

import com.roland.android.data_remote.network.service.NewsService
import com.roland.android.data_remote.utils.Converters.convertToArticle
import com.roland.android.data_remote.utils.Converters.convertToSourceDetail
import com.roland.android.data_repository.data_source.remote.RemoteNewsDataSource
import com.roland.android.domain.model.Article
import com.roland.android.domain.model.SourceDetail
import com.roland.android.domain.model.UseCaseException
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class RemoteNewsDataSourceImpl(
	private val newsService: NewsService
) : RemoteNewsDataSource {

	override fun fetchTrendingNews(
		selectedCategories: String,
		selectedSources: String,
		languageCode: String,
	): Flow<List<Article>> = flow {
		emit(newsService.fetchTrendingNews(selectedCategories, selectedSources, languageCode))
	}.map { articleList ->
		articleList.map { articleModel ->
			convertToArticle(articleModel)
		}
	}.catch { throw UseCaseException.NewsException(it) }

	override fun fetchRecommendedNews(category: String, languageCode: String): Flow<List<Article>> = flow {
		emit(newsService.fetchRecommendedNews(category, languageCode))
	}.map { articleList ->
		articleList.map { articleModel ->
			convertToArticle(articleModel)
		}
	}.catch { throw UseCaseException.NewsException(it) }

	override fun fetchNewsByCategory(
		category: String,
		languageCode: String,
		page: Int,
	): List<Article> = runBlocking {
		async {
			newsService.fetchNewsByCategory(category, languageCode, page)
				.map { articleModel ->
					convertToArticle(articleModel)
				}
		}.await()
	}

	override fun fetchNewsBySource(source: String, languageCode: String, page: Int): List<Article> = runBlocking {
		async {
			newsService.fetchNewsByCategory(source, languageCode, page)
				.map { articleModel ->
					convertToArticle(articleModel)
				}
		}.await()
	}

	override fun searchNews(
		query: String,
		categories: String,
		sources: String,
		languageCode: String,
		page: Int,
	): List<Article> = runBlocking {
		async {
			newsService.searchNews(query, categories, sources, languageCode, page)
				.map { articleModel ->
					convertToArticle(articleModel)
				}
		}.await()
	}

	override fun fetchAllSources(): Flow<List<SourceDetail>> = flow {
		emit(newsService.fetchAllSources())
	}.map { sourceList ->
		sourceList.map { sourceModel ->
			convertToSourceDetail(sourceModel)
		}
	}.catch { throw UseCaseException.NewsException(it) }

}