package com.roland.android.data_local.data_source

import com.roland.android.data_local.database.HistoryDao
import com.roland.android.data_local.database.NewsDao
import com.roland.android.data_local.database.SavedNewsDao
import com.roland.android.data_local.database.SourceDao
import com.roland.android.data_local.datastore.SubscribedCategoryStore
import com.roland.android.data_local.utils.Converters.SEPARATOR
import com.roland.android.data_local.utils.Converters.convertToArticle
import com.roland.android.data_local.utils.Converters.convertToArticleEntity
import com.roland.android.data_local.utils.Converters.convertToSource
import com.roland.android.data_local.utils.Converters.convertToSourceDetail
import com.roland.android.data_repository.data_source.local.LocalNewsDataSource
import com.roland.android.domain.model.Article
import com.roland.android.domain.model.CategoryModel
import com.roland.android.domain.model.Source
import com.roland.android.domain.model.SourceDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalNewsDataSourceImpl(
	private val newsDao: NewsDao,
	private val historyDao: HistoryDao,
	private val savedNewsDao: SavedNewsDao,
	private val sourceDao: SourceDao,
	private val categoryStore: SubscribedCategoryStore
) : LocalNewsDataSource {
	override fun fetchTrendingNews(categories: List<String>): Flow<List<Article>> {
		val categoryList = categories.joinToString { SEPARATOR }
		return newsDao.fetchTrendingNews(categoryList)
			.map { articleEntities ->
				articleEntities.map { it.convertToArticle() }
			}
	}

	override fun saveTrendingNews(categories: List<String>, news: List<Article>) {
		val categoryList = categories.joinToString { SEPARATOR }
		val trendingNews = news.map { it.convertToArticleEntity(categoryList) }
		newsDao.saveTrendingNews(trendingNews)
	}

	override fun fetchRecommendedNews(category: String): Flow<List<Article>> {
		return newsDao.fetchRecommendedNews(category)
			.map { articleEntities ->
				articleEntities.map { it.convertToArticle() }
			}
	}

	override fun saveRecommendedNews(category: String, news: List<Article>) {
		val recommendedNews = news.map { it.convertToArticleEntity(category) }
		newsDao.saveRecommendedNews(recommendedNews)
	}

	override fun clearCachedNews() {
		newsDao.clearCachedNews()
	}

	override fun fetchAllSources(): Flow<List<SourceDetail>> {
		return sourceDao.fetchAllSources()
			.map { sourceDetailEntities ->
				sourceDetailEntities.map { it.convertToSourceDetail() }
			}
	}

	override fun saveAllSources(sources: List<SourceDetail>) {
		val sourceEntities = sources.map { it.convertToSourceDetail() }
		sourceDao.saveAllSources(sourceEntities)
	}

	override fun fetchSourceDetails(source: Source): Flow<SourceDetail> {
		return sourceDao.fetchSourceDetails(source.id)
			.map { it.convertToSourceDetail() }
	}

	override fun fetchSavedArticles(): List<Article> {
		return savedNewsDao.fetchSavedArticles()
			.map { it.convertToArticle() }
	}

	override fun fetchReadingHistory(): List<Article> {
		return historyDao.fetchReadingHistory()
			.map { it.convertToArticle() }
	}

	override fun fetchSubscribedSources(): Flow<List<Source>> {
		return sourceDao.fetchAllSources()
			.map { sourceEntities ->
				sourceEntities.map { it.convertToSource() }
			}
	}

	override fun fetchSubscribedCategories(): Flow<List<CategoryModel>> {
		return categoryStore.getSubscribedCategories()
	}
}