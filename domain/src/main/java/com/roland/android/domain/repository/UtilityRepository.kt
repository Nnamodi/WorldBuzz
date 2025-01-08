package com.roland.android.domain.repository

import com.roland.android.domain.model.Article
import com.roland.android.domain.model.CategoryModel
import com.roland.android.domain.model.Source

interface UtilityRepository {

	fun updateSubscribedSources(sources: List<Source>)

	fun updateSubscribedCategories(categories: List<CategoryModel>)

	fun saveArticle(article: Article)

	fun unsaveArticle(article: Article)

	fun addArticleToHistory(article: Article)

	fun clearReadingHistory()

	fun shareArticle(url: String)

}