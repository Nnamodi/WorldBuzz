package com.roland.android.data_repository

import com.roland.android.data_repository.data_source.local.UtilityDataSource
import com.roland.android.data_repository.repository.UtilityRepositoryImpl
import com.roland.android.domain.model.Article
import com.roland.android.domain.model.CategoryModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class UtilityRepositoryImplTest {
	private val utilityDataSource = mock<UtilityDataSource>()
	@OptIn(ExperimentalCoroutinesApi::class)
	private val scope = TestScope(UnconfinedTestDispatcher())
	private val utilityRepository = UtilityRepositoryImpl(utilityDataSource, scope, mock())

	@Test
	fun testSaveArticle() = runTest {
		val article = Article(id = 15)
		utilityRepository.saveArticle(article)
		verify(utilityDataSource).saveArticle(article)
	}

	@Test
	fun testUnsaveArticle() = runTest {
		val article = Article(id = 8)
		utilityRepository.unsaveArticle(article)
		verify(utilityDataSource).unsaveArticle(article)
	}

	@Test
	fun testUpdateSubscribedSources() = runTest {
		val sources = sampleNewsSourcesII.take(4)
		utilityRepository.updateSubscribedSources(sources)
		verify(utilityDataSource).updateSubscribedSources(sources)
	}

	@Test
	fun testUpdateSubscribedCategories() = runTest {
		val categories = CategoryModel.entries.takeLast(3)
		utilityRepository.updateSubscribedCategories(categories)
		verify(utilityDataSource).updateSubscribedCategories(categories)
	}

	@Test
	fun testAddArticleToHistory() = runTest {
		val article = Article(id = 10)
		utilityRepository.addArticleToHistory(article)
		verify(utilityDataSource).addArticleToHistory(article)
	}

	@Test
	fun testClearReadingHistory() = runTest {
		utilityRepository.clearReadingHistory()
		verify(utilityDataSource).clearReadingHistory()
	}

}