package com.roland.android.data_repository.data_source.local

import com.roland.android.domain.model.Article
import com.roland.android.domain.model.CategoryModel
import com.roland.android.domain.model.Source
import com.roland.android.domain.model.SourceDetail
import kotlinx.coroutines.flow.Flow

interface LocalNewsDataSource {

	fun fetchTrendingNews(categories: List<String>): Flow<List<Article>>

	fun saveTrendingNews(
		categories: List<String>,
		news: List<Article>
	)

	fun fetchRecommendedNews(category: String): Flow<List<Article>>

	fun saveRecommendedNews(
		category: String,
		news: List<Article>
	)

	fun clearCachedNews()

	fun fetchAllSources(): Flow<List<SourceDetail>>

	fun saveAllSources(sources: List<SourceDetail>)

	fun fetchSourceDetails(source: Source): Flow<SourceDetail>

	fun fetchSavedArticles(): List<Article>

	fun fetchReadingHistory(): List<Article>

	fun fetchSubscribedSources(): Flow<List<Source>>

	fun fetchSubscribedCategories(): Flow<List<CategoryModel>>

}