package com.roland.android.domain.repository

import androidx.paging.PagingData
import com.roland.android.domain.model.Article
import com.roland.android.domain.model.CategoryModel
import com.roland.android.domain.model.Source
import com.roland.android.domain.model.SourceDetail
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

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
		languageCode: String
	): Flow<PagingData<Article>>

	fun fetchNewsBySource(
		source: String,
		languageCode: String
	): Flow<PagingData<Article>>

	fun searchNews(
		query: String,
		category: String = "",
		sources: String = ""
	): Flow<PagingData<Article>>

	fun fetchAllSources(): Flow<List<SourceDetail>>

	fun fetchSourceDetails(source: Source): Flow<SourceDetail>

	fun fetchSavedArticles(): Flow<PagingData<Article>>

	fun fetchReadingHistory(): Flow<PagingData<Article>>

	fun fetchSubscribedSources(): Flow<List<Source>>

	fun fetchSubscribedCategories(): Flow<List<CategoryModel>>

}