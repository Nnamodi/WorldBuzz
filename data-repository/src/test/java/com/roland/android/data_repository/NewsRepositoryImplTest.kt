package com.roland.android.data_repository

import com.roland.android.data_repository.data_source.local.LocalNewsDataSource
import com.roland.android.data_repository.data_source.remote.RemoteNewsDataSource
import com.roland.android.data_repository.repository.NewsRepositoryImpl
import com.roland.android.domain.model.CategoryModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class NewsRepositoryImplTest {
	private val localNewsDataSource = mock<LocalNewsDataSource>()
	private val remoteNewsDataSource = mock<RemoteNewsDataSource>()
	@OptIn(ExperimentalCoroutinesApi::class)
	private val scope = TestScope(UnconfinedTestDispatcher())
	private val newsRepository = NewsRepositoryImpl(localNewsDataSource, remoteNewsDataSource, scope)

	@Test
	fun testLocalNewsRepo() = runTest {
		whenever(localNewsDataSource.fetchTrendingNews(listOf(""))).thenReturn(flowOf(sampleNewsData))
		whenever(localNewsDataSource.fetchRecommendedNews("")).thenReturn(flowOf(sampleNewsData))
		whenever(localNewsDataSource.fetchSavedArticles()).thenReturn(sampleNewsData)
		whenever(localNewsDataSource.fetchReadingHistory()).thenReturn(sampleNewsData)

		val trendingNews = newsRepository.fetchTrendingNews("", "", "").first()
		val recommendedNews = newsRepository.fetchRecommendedNews("", "").first()
		val savedNews = newsRepository.fetchSavedArticles().first()
		val readNews = newsRepository.fetchReadingHistory().first()

		val fetchedData = listOf(trendingNews, recommendedNews, savedNews, readNews)
		val expectedData = listOf(sampleNewsData, sampleNewsData, sampleNewsPagingData, sampleNewsPagingData)
		assertEquals(fetchedData, expectedData)
	}

	@Test
	fun testRemoteNewsRepo() = runTest {
		whenever(remoteNewsDataSource.fetchTrendingNews("", "", "")).thenReturn(flowOf(sampleNewsData))
		whenever(remoteNewsDataSource.fetchRecommendedNews("", "")).thenReturn(flowOf(sampleNewsData))
		whenever(remoteNewsDataSource.fetchNewsByCategory("", "", 1)).thenReturn(sampleNewsData)
		whenever(remoteNewsDataSource.fetchNewsBySource("", "", 1)).thenReturn(sampleNewsData)
		whenever(remoteNewsDataSource.searchNews("", "", "", "", 1)).thenReturn(sampleNewsData)

		val trendingNews = newsRepository.fetchTrendingNews("", "", "").first()
		val recommendedNews = newsRepository.fetchRecommendedNews("", "").first()
		val newsByCategory = newsRepository.fetchNewsByCategory("", "").first()
		val newsBySource = newsRepository.fetchNewsBySource("", "").first()
		val searchedNews = newsRepository.searchNews("", "", "", "").first()

		val fetchedData = listOf(trendingNews, recommendedNews, newsByCategory, newsBySource, searchedNews)
		val expectedData = listOf(sampleNewsData, sampleNewsData, sampleNewsPagingData, sampleNewsPagingData, sampleNewsPagingData)
		assertEquals(fetchedData, expectedData)
	}

	@Test
	fun testGetSources() = runTest {
		whenever(remoteNewsDataSource.fetchAllSources()).thenReturn(flowOf(sampleNewsSourcesII))
		whenever(localNewsDataSource.fetchAllSources()).thenReturn(flowOf(sampleNewsSourcesII))
		whenever(localNewsDataSource.fetchSourceDetails(sampleNewsSource)).thenReturn(flowOf(sampleNewsSourceDetail))
		whenever(localNewsDataSource.fetchSubscribedSources()).thenReturn(flowOf(sampleNewsSources))

		val allSources = newsRepository.fetchAllSources().first()
		val sourceDetails = newsRepository.fetchSourceDetails(sampleNewsSource).first()
		val subscribedSources = newsRepository.fetchSubscribedSources().first()

		val fetchedData = listOf(allSources, sourceDetails, subscribedSources)
		val expectedData = listOf(sampleNewsSourcesII, sampleNewsSourceDetail, sampleNewsSources)
		assertEquals(fetchedData, expectedData)
	}

	@Test
	fun testGetSubscribedCategories() = runTest {
		val categories = CategoryModel.entries.takeLast(4)
		whenever(localNewsDataSource.fetchSubscribedCategories()).thenReturn(flowOf(categories))

		val subscribedCategories = newsRepository.fetchSubscribedCategories().first()
		assertEquals(subscribedCategories, categories)
	}
}