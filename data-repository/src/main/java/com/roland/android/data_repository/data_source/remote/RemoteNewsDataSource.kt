package com.roland.android.data_repository.data_source.remote

import com.roland.android.domain.model.Article
import com.roland.android.domain.model.SourceDetail
import kotlinx.coroutines.flow.Flow

interface RemoteNewsDataSource {

	fun fetchTrendingNews(
		selectedCategories: String,
		selectedSources: String,
		languageCode: String
	): Flow<List<Article>>

	fun fetchRecommendedNews(
		category: String,
		languageCode: String
	): Flow<List<Article>>

	fun fetchNewsByCategory(
		category: String,
		languageCode: String,
		page: Int
	): List<Article>

	fun fetchNewsBySource(
		source: String,
		languageCode: String,
		page: Int
	): List<Article>

	fun searchNews(
		query: String,
		categories: String = "",
		sources: String = "",
		languageCode: String,
		page: Int
	): List<Article>

	fun fetchAllSources(): Flow<List<SourceDetail>>

}