package com.roland.android.data_local.utils

import com.roland.android.data_local.entity.ArticleEntity
import com.roland.android.data_local.entity.ReadArticleEntity
import com.roland.android.data_local.entity.SavedArticleEntity
import com.roland.android.data_local.entity.SourceDetailEntity
import com.roland.android.domain.model.Article
import com.roland.android.domain.model.Source
import com.roland.android.domain.model.SourceDetail

object Converters {
	const val SEPARATOR = "|"

	fun ArticleEntity.convertToArticle() = Article(
		id = id,
		source = source,
		author = author,
		title = title,
		description = description,
		url = url,
		imageUrl = imageUrl,
		publishedAt = publishedAt,
		content = content
	)

	fun SavedArticleEntity.convertToArticle() = Article(
		id = id,
		source = source,
		author = author,
		title = title,
		description = description,
		url = url,
		imageUrl = imageUrl,
		publishedAt = publishedAt,
		content = content
	)

	fun ReadArticleEntity.convertToArticle() = Article(
		id = id,
		source = source,
		author = author,
		title = title,
		description = description,
		url = url,
		imageUrl = imageUrl,
		publishedAt = publishedAt,
		content = content
	)

	fun Article.convertToArticleEntity(categories: String) = ArticleEntity(
		id = id,
		source = source,
		author = author,
		title = title,
		description = description,
		url = url,
		imageUrl = imageUrl,
		publishedAt = publishedAt,
		content = content,
		categories = categories
	)

	fun Article.convertToReadArticleEntity() = ReadArticleEntity(
		id = id,
		source = source,
		author = author,
		title = title,
		description = description,
		url = url,
		imageUrl = imageUrl,
		publishedAt = publishedAt,
		content = content
	)

	fun Article.convertToSavedArticleEntity() = SavedArticleEntity(
		id = id,
		source = source,
		author = author,
		title = title,
		description = description,
		url = url,
		imageUrl = imageUrl,
		publishedAt = publishedAt,
		content = content
	)

	fun SourceDetailEntity.convertToSourceDetail() = SourceDetail(
		id = id,
		name = name,
		description = description,
		url = url,
		category = category,
		language = language,
		country = country,
		subscribed = subscribed
	)

	fun SourceDetail.convertToSourceDetail() = SourceDetailEntity(
		id = id,
		name = name,
		description = description,
		url = url,
		category = category,
		language = language,
		country = country,
		subscribed = subscribed
	)

	fun SourceDetailEntity.convertToSource() = Source(
		id = id,
		name = name,
		subscribed = subscribed
	)
}