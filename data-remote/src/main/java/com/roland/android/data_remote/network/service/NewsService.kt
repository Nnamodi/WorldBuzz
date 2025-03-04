package com.roland.android.data_remote.network.service

import com.roland.android.data_remote.network.model.ArticleListModel
import com.roland.android.data_remote.network.model.SourceListModel
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

	@GET("top-headlines")
	suspend fun fetchTrendingNews(
		@Query("category") selectedCategories: String,
		@Query("sources") selectedSources: String,
		@Query("language") languageCode: String,
		@Query("country") country: String,
		@Query("apiKey") apiKey: String
	): ArticleListModel

	@GET("top-headlines")
	suspend fun fetchRecommendedNews(
		@Query("category") category: String,
		@Query("language") languageCode: String,
		@Query("country") country: String,
		@Query("apiKey") apiKey: String
	): ArticleListModel

	@GET("top-headlines")
	suspend fun fetchNewsByCategory(
		@Query("category") category: String,
		@Query("language") languageCode: String,
		@Query("country") country: String,
		@Query("apiKey") apiKey: String,
		@Query("page") page: Int
	): ArticleListModel

	@GET("everything")
	suspend fun fetchNewsBySource(
		@Query("sources") source: String,
		@Query("language") languageCode: String,
		@Query("country") country: String,
		@Query("apiKey") apiKey: String,
		@Query("page") page: Int
	): ArticleListModel

	@GET("top-headlines")
	suspend fun searchNews(
		@Query("q") query: String = "",
		@Query("category") category: String = "",
		@Query("sources") sources: String = "",
		@Query("country") country: String,
		@Query("apiKey") apiKey: String,
		@Query("page") page: Int
	): ArticleListModel

	@GET("top-headlines/sources")
	suspend fun fetchAllSources(
		@Query("language") language: String,
		@Query("country") country: String,
		@Query("apiKey") apiKey: String
	): SourceListModel

}