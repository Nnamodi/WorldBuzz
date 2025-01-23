package com.roland.android.data_repository.data_source.local

import com.roland.android.domain.model.Article
import com.roland.android.domain.model.CategoryModel
import com.roland.android.domain.model.SourceDetail

interface UtilityDataSource {

	suspend fun updateSubscribedSources(sources: List<SourceDetail>)

	suspend fun updateSubscribedCategories(categories: List<CategoryModel>)

	suspend fun saveArticle(article: Article)

	suspend fun unsaveArticle(article: Article)

	suspend fun addArticleToHistory(article: Article)

	suspend fun clearReadingHistory()

}