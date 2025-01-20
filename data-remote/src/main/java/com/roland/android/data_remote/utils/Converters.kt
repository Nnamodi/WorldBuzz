package com.roland.android.data_remote.utils

import com.roland.android.data_remote.network.model.ArticleModel
import com.roland.android.data_remote.network.model.SourceDetailModel
import com.roland.android.data_remote.network.model.SourceModel
import com.roland.android.domain.model.Article
import com.roland.android.domain.model.Source
import com.roland.android.domain.model.SourceDetail

object Converters {
	fun convertToArticle(articleModel: ArticleModel) = Article(
		source = convertToSource(articleModel.source),
		author = articleModel.author,
		title = articleModel.title,
		description = articleModel.description,
		url = articleModel.url,
		imageUrl = articleModel.imageUrl,
		publishedAt = articleModel.publishedAt,
		content = articleModel.content
	)

	private fun convertToSource(sourceModel: SourceModel) = Source(
		id = sourceModel.id,
		name = sourceModel.name
	)

	fun convertToSourceDetail(sourceDetailModel: SourceDetailModel) = SourceDetail(
		id = sourceDetailModel.id,
		name = sourceDetailModel.name,
		description = sourceDetailModel.description,
		url = sourceDetailModel.url,
		category = sourceDetailModel.category,
		language = sourceDetailModel.language,
		country = sourceDetailModel.country,
	)
}