package com.roland.android.data_remote

import com.roland.android.data_remote.data_source.RemoteNewsDataSourceImpl
import com.roland.android.data_remote.network.service.NewsService
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class RemoteNewsDataSourceImplTest {

	private val apiKey = ""
	private val newsService = mock<NewsService>()
	private val remoteNewsDataSource = RemoteNewsDataSourceImpl(newsService, apiKey)

	@Test
	fun testFetchTrendingNews() = runTest {
		whenever(newsService.fetchTrendingNews("", "", "", "us", apiKey)).thenReturn(remoteNewsList)
		val remoteData = remoteNewsDataSource.fetchTrendingNews("", "", "").first()
		assertEquals(remoteData, expectedNewsData)
	}

	@Test
	fun testFetchRecommendedNews() = runTest {
		whenever(newsService.fetchRecommendedNews("", "", "us", apiKey)).thenReturn(remoteNewsList)
		val remoteData = remoteNewsDataSource.fetchRecommendedNews("", "").first()
		assertEquals(remoteData, expectedNewsData)
	}

	@Test
	fun testFetchNewsByCategory() = runTest {
		whenever(newsService.fetchNewsByCategory("", "", "us", apiKey, 1)).thenReturn(remoteNewsList)
		val remoteData = remoteNewsDataSource.fetchNewsByCategory("", "", 1)
		assertEquals(remoteData, expectedNewsData)
	}

	@Test
	fun testFetchNewsBySource() = runTest {
		whenever(newsService.fetchNewsBySource("", "", "us", apiKey, 2)).thenReturn(remoteNewsList)
		val remoteData = remoteNewsDataSource.fetchNewsBySource("", "", 2)
		assertEquals(remoteData, expectedNewsData)
	}

	@Test
	fun testSearchNews() = runTest {
		whenever(newsService.searchNews("news", "", "", "", "us", apiKey, 3)).thenReturn(remoteNewsList)
		val remoteData = remoteNewsDataSource.searchNews("", "", "", "", 3)
		assertEquals(remoteData, expectedNewsData)
	}

	@Test
	fun testFetchAllSources() = runTest {
		whenever(newsService.fetchAllSources("us", apiKey)).thenReturn(remoteNewsSourcesList)
		val remoteData = remoteNewsDataSource.fetchAllSources().first()
		assertEquals(remoteData, expectedNewsSources)
	}

}