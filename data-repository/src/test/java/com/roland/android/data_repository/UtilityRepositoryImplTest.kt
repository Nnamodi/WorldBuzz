package com.roland.android.data_repository

import com.roland.android.data_repository.data_source.local.LocalNewsDataSource
import com.roland.android.data_repository.data_source.local.UtilityDataSource
import com.roland.android.data_repository.repository.UtilityRepositoryImpl
import com.roland.android.domain.model.CategoryModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock

class UtilityRepositoryImplTest {
	private val localNewsDataSource = mock<LocalNewsDataSource>()
	private val utilityDataSource = mock<UtilityDataSource>()
	@OptIn(ExperimentalCoroutinesApi::class)
	private val scope = TestScope(UnconfinedTestDispatcher())
	private val utilityRepositoryImpl = UtilityRepositoryImpl(utilityDataSource, scope, mock())

	@Test
	fun testUpdateSubscribedSources() = runTest {
		val sources = sampleNewsSources.take(4)
		utilityRepositoryImpl.updateSubscribedSources(sources)
		val result = localNewsDataSource.fetchSubscribedSources().first()
		assertEquals(sources, result)
	}

	@Test
	fun testSaveArticle() = runTest {
		val categories = CategoryModel.entries.takeLast(3)
		utilityRepositoryImpl.updateSubscribedCategories(categories)
		val result = localNewsDataSource.fetchSubscribedCategories()
		assertEquals(categories, result)
	}
}