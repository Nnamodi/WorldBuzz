package com.roland.android.data_remote.network.model

import com.squareup.moshi.Json

data class ArticleListModel(
	@Json(name = "totalResults")
	val totalResults: Int = 0,
	@Json(name = "articles")
	val articles: List<ArticleModel> = emptyList()
)

data class SourceListModel(
	@Json(name = "sources")
	val sources: List<SourceDetailModel> = emptyList()
)
