package com.roland.android.data_remote.network.model

import com.squareup.moshi.Json

data class ArticleModel(
	@Json(name = "source")
	val source: SourceModel = SourceModel(),
	@Json(name = "author")
	val author: String = "",
	@Json(name = "title")
	val title: String = "",
	@Json(name = "description")
	val description: String = "",
	@Json(name = "url")
	val url: String = "",
	@Json(name = "urlToImage")
	val imageUrl: String = "",
	@Json(name = "publishedAt")
	val publishedAt: String = "",
	@Json(name = "content")
	val content: String = ""
)
