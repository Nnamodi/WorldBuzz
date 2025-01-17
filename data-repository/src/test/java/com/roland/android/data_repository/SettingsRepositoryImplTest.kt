package com.roland.android.data_repository

import com.roland.android.data_repository.data_source.local.SettingsDataSource
import com.roland.android.data_repository.repository.SettingsRepositoryImpl
import com.roland.android.domain.model.LanguageModel
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

class SettingsRepositoryImplTest {
	private val settingsDataSource = mock<SettingsDataSource>()
	@OptIn(ExperimentalCoroutinesApi::class)
	private val scope = TestScope(UnconfinedTestDispatcher())
	private val settingsRepositoryImpl = SettingsRepositoryImpl(settingsDataSource, scope)

	@Test
	fun testGetLanguage() = runTest {
		val language = LanguageModel.French
		whenever(settingsDataSource.getSelectedLanguage()).thenReturn(flowOf(language))

		val result = settingsRepositoryImpl.getSelectedLanguage().first()
		assertEquals(language, result)
	}

	@Test
	fun testSaveLanguage() = runTest {
		val language = LanguageModel.French
		settingsRepositoryImpl.selectLanguage(language.code)
		val result = settingsDataSource.getSelectedLanguage()
		assertEquals(language, result)
	}
}