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
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class SettingsRepositoryImplTest {
	private val settingsDataSource = mock<SettingsDataSource>()
	@OptIn(ExperimentalCoroutinesApi::class)
	private val scope = TestScope(UnconfinedTestDispatcher())
	private val settingsRepository = SettingsRepositoryImpl(settingsDataSource, scope)

	@Test
	fun testGetLanguage() = runTest {
		val language = LanguageModel.French
		whenever(settingsDataSource.getSelectedLanguage()).thenReturn(flowOf(language))

		val result = settingsRepository.getSelectedLanguage().first()
		assertEquals(language, result)
	}

	@Test
	fun testSaveLanguage() = runTest {
		val languageCode = LanguageModel.French.code
		settingsRepository.selectLanguage(languageCode)
		verify(settingsDataSource).selectLanguage(languageCode)
	}

	@Test
	fun testGetTextSize() = runTest {
		val textSize = 18
		whenever(settingsDataSource.getSelectedTextSize()).thenReturn(flowOf(textSize))

		val result = settingsRepository.getSelectedTextSize().first()
		assertEquals(textSize, result)
	}

	@Test
	fun testSaveTextSize() = runTest {
		val textSize = 22
		settingsRepository.selectTextSize(textSize)
		verify(settingsDataSource).selectTextSize(textSize)
	}

	@Test
	fun testGetEnableReadingHistory() = runTest {
		val enable = false
		whenever(settingsDataSource.isReadingHistoryEnabled()).thenReturn(flowOf(enable))

		val result = settingsRepository.enableReadingHistory(enable).first()
		assertEquals(enable, result)
	}

	@Test
	fun testSetEnableHistory() = runTest {
		val enable = false
		settingsRepository.enableReadingHistory(enable)
		verify(settingsDataSource).enableReadingHistory(enable)
	}
}