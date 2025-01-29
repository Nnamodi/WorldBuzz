package com.roland.android.data_local

import com.roland.android.data_local.data_source.UtilityDataSourceImpl
import com.roland.android.data_local.database.HistoryDao
import com.roland.android.data_local.database.SavedNewsDao
import com.roland.android.data_local.database.SourceDao
import com.roland.android.data_local.datastore.SubscribedCategoryStore
import com.roland.android.data_local.utils.Converters.convertToReadArticleEntity
import com.roland.android.data_local.utils.Converters.convertToSavedArticleEntity
import com.roland.android.data_local.utils.Converters.convertToSourceDetail
import com.roland.android.domain.model.Article
import com.roland.android.domain.model.CategoryModel
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class UtilityDataSourceImplTest {
	private val historyDao = mock<HistoryDao>()
	private val savedNewsDao = mock<SavedNewsDao>()
	private val sourceDao = mock<SourceDao>()
	private val categoryStore = mock<SubscribedCategoryStore>()
	private val utilityDataSource = UtilityDataSourceImpl(historyDao, savedNewsDao, sourceDao, categoryStore)

	@Test
	fun testSaveArticle() = runTest {
		val article = Article(id = 15)
		utilityDataSource.saveArticle(article)
		verify(savedNewsDao).saveArticle(article.convertToSavedArticleEntity())
	}

	@Test
	fun testUnsaveArticle() = runTest {
		val article = Article(id = 8)
		utilityDataSource.unsaveArticle(article)
		verify(savedNewsDao).unsaveArticle(article.convertToSavedArticleEntity())
	}

	@Test
	fun testUpdateSubscribedSources() = runTest {
		val sources = expectedNewsSources.take(4)
		utilityDataSource.updateSubscribedSources(sources)
		val sourceDetailEntities = sources.map { it.convertToSourceDetail() }
		verify(sourceDao).saveAllSources(sourceDetailEntities)
	}

	@Test
	fun testUpdateSubscribedCategories() = runTest {
		val categories = CategoryModel.entries.takeLast(3)
		utilityDataSource.updateSubscribedCategories(categories)
		verify(categoryStore).updateSubscribedCategories(categories)
	}

	@Test
	fun testAddArticleToHistory() = runTest {
		val article = Article(id = 10)
		utilityDataSource.addArticleToHistory(article)
		verify(historyDao).addArticleToHistory(article.convertToReadArticleEntity())
	}

	@Test
	fun testClearReadingHistory() = runTest {
		utilityDataSource.clearReadingHistory()
		verify(historyDao).clearReadingHistory()
	}

}