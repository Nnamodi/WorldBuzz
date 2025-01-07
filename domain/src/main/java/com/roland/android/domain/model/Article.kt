package com.roland.android.domain.model

data class Article(
	val source: Source = Source(),
	val author: String = "",
	val title: String = "",
	val description: String = "",
	val url: String = "",
	val imageUrl: String = "",
	val publishedAt: String = "",
	val content: String = ""
)
