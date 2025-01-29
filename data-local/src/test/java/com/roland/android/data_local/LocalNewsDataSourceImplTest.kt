package com.roland.android.data_local

import com.roland.android.data_local.data_source.LocalNewsDataSourceImpl
import com.roland.android.data_local.database.HistoryDao
import com.roland.android.data_local.database.NewsDao
import com.roland.android.data_local.database.SavedNewsDao
import com.roland.android.data_local.database.SourceDao
import com.roland.android.data_local.datastore.SubscribedCategoryStore
import com.roland.android.domain.model.CategoryModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class LocalNewsDataSourceImplTest {
	private val newsDao = mock<NewsDao>()
	private val historyDao = mock<HistoryDao>()
	private val savedNewsDao = mock<SavedNewsDao>()
	private val sourceDao = mock<SourceDao>()
	private val categoryStore = mock<SubscribedCategoryStore>()
	private val localNewsDataSource = LocalNewsDataSourceImpl(newsDao, historyDao, savedNewsDao, sourceDao, categoryStore)

	@Test
	fun testFetchLocalNews() = runTest {
		whenever(newsDao.fetchTrendingNews("|")).thenReturn(flowOf(localTrendingNewsData))
		whenever(newsDao.fetchRecommendedNews("")).thenReturn(flowOf(localRecommendedNewsData))
		whenever(historyDao.fetchReadingHistory()).thenReturn(localReadNewsData)
		whenever(savedNewsDao.fetchSavedArticles()).thenReturn(localSavedNewsData)

		val trending = localNewsDataSource.fetchTrendingNews(listOf("")).first()
		val recommended = localNewsDataSource.fetchRecommendedNews("").first()
		val read = localNewsDataSource.fetchReadingHistory()
		val saved = localNewsDataSource.fetchSavedArticles()

		val localData = listOf(trending, recommended, read, saved)
		val expectedData = listOf(expectedNewsData, expectedNewsData, expectedNewsData, expectedNewsData)
		assertEquals(localData, expectedData)
	}

	@Test
	fun testFetchSources() = runTest {
		whenever(sourceDao.fetchAllSources()).thenReturn(flowOf(localNewsSources))
		whenever(sourceDao.fetchSubscribedSources()).thenReturn(flowOf(localNewsSources))
		whenever(sourceDao.fetchSourceDetails("nri")).thenReturn(flowOf(localNewsSource))

		val allSources = localNewsDataSource.fetchAllSources().first()
		val subscribedSources = localNewsDataSource.fetchSubscribedSources().first()
		val sourceDetail = localNewsDataSource.fetchSourceDetails(newsSource).first()

		assertEquals(
			listOf(allSources, subscribedSources, sourceDetail),
			listOf(expectedNewsSources, newsSources, expectedNewsSource)
		)
	}

	@Test
	fun testFetchSubscribedCategory() = runTest {
		val categories = CategoryModel.entries
		whenever(categoryStore.getSubscribedCategories()).thenReturn(flowOf(categories))
		val localData = localNewsDataSource.fetchSubscribedCategories().first()
		assertEquals(localData, categories)
	}

	@Test
	fun testSaveTrendingNews() = runTest {
		localNewsDataSource.saveTrendingNews(listOf(""), expectedNewsData)
		verify(newsDao).saveTrendingNews(localTrendingNewsData)
	}

	@Test
	fun testSaveRecommendedNews() = runTest {
		localNewsDataSource.saveRecommendedNews("", expectedNewsData)
		verify(newsDao).saveRecommendedNews(localRecommendedNewsData)
	}

	@Test
	fun testSaveAllSources() = runTest {
		localNewsDataSource.saveAllSources(expectedNewsSources)
		verify(sourceDao).saveAllSources(localNewsSources)
	}
}