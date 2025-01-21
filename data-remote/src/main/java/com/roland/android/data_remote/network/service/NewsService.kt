package com.roland.android.data_remote.network.service

import com.roland.android.data_remote.network.model.ArticleModel
import com.roland.android.data_remote.network.model.SourceDetailModel
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

	@GET("/v2/top-headlines")
	suspend fun fetchTrendingNews(
		@Query("category") selectedCategories: String,
		@Query("sources") selectedSources: String,
		@Query("language") languageCode: String
	): List<ArticleModel>

	@GET("/v2/top-headlines")
	suspend fun fetchRecommendedNews(
		@Query("category") category: String,
		@Query("language") languageCode: String
	): List<ArticleModel>

	@GET("/v2/top-headlines")
	suspend fun fetchNewsByCategory(
		@Query("category") category: String,
		@Query("language") languageCode: String,
		@Query("page") page: Int
	): List<ArticleModel>

	@GET("/v2/everything")
	suspend fun fetchNewsBySource(
		@Query("sources") source: String,
		@Query("language") languageCode: String,
		@Query("page") page: Int
	): List<ArticleModel>

	@GET("/v2/everything")
	suspend fun searchNews(
		@Query("q") query: String = "",
		@Query("category") categories: String = "",
		@Query("sources") sources: String = "",
		@Query("language") languageCode: String,
		@Query("page") page: Int
	): List<ArticleModel>

	@GET("/v2/top-headlines/sources")
	suspend fun fetchAllSources(): List<SourceDetailModel>

}