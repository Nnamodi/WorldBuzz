package com.roland.android.data_local.data_source

import com.roland.android.data_local.database.HistoryDao
import com.roland.android.data_local.database.SavedNewsDao
import com.roland.android.data_local.database.SourceDao
import com.roland.android.data_local.datastore.SubscribedCategoryStore
import com.roland.android.data_local.utils.Converters.convertToReadArticleEntity
import com.roland.android.data_local.utils.Converters.convertToSavedArticleEntity
import com.roland.android.data_local.utils.Converters.convertToSourceDetail
import com.roland.android.data_repository.data_source.local.UtilityDataSource
import com.roland.android.domain.model.Article
import com.roland.android.domain.model.CategoryModel
import com.roland.android.domain.model.SourceDetail

class UtilityDataSourceImpl(
	private val historyDao: HistoryDao,
	private val savedNewsDao: SavedNewsDao,
	private val sourceDao: SourceDao,
	private val categoryStore: SubscribedCategoryStore
) : UtilityDataSource {
	override suspend fun updateSubscribedSources(sources: List<SourceDetail>) {
		val sourceList = sources.map { it.convertToSourceDetail() }
		sourceDao.saveAllSources(sourceList)
	}

	override suspend fun updateSubscribedCategories(categories: List<CategoryModel>) {
		categoryStore.updateSubscribedCategories(categories)
	}

	override suspend fun saveArticle(article: Article) {
		val articleEntity = article.convertToSavedArticleEntity()
		savedNewsDao.saveArticle(articleEntity)
	}

	override suspend fun unsaveArticle(article: Article) {
		val articleEntity = article.convertToSavedArticleEntity()
		savedNewsDao.unsaveArticle(articleEntity)
	}

	override suspend fun addArticleToHistory(article: Article) {
		val articleEntity = article.convertToReadArticleEntity()
		historyDao.addArticleToHistory(articleEntity)
	}

	override suspend fun clearReadingHistory() {
		historyDao.clearReadingHistory()
	}
}