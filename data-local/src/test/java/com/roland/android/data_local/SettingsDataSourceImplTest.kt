package com.roland.android.data_local

import com.roland.android.data_local.data_source.SettingsDataSourceImpl
import com.roland.android.data_local.datastore.SettingsStore
import com.roland.android.domain.model.LanguageModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class SettingsDataSourceImplTest {
	private val settingsStore = mock<SettingsStore>()
	private val settingsDataSource = SettingsDataSourceImpl(settingsStore)

	@Test
	fun testGetLanguage() = runTest {
		val language = LanguageModel.French
		whenever(settingsStore.getSelectedLanguage()).thenReturn(flowOf(language))

		val result = settingsDataSource.getSelectedLanguage().first()
		assertEquals(language, result)
	}

	@Test
	fun testSaveLanguage() = runTest {
		val languageCode = LanguageModel.French.code
		settingsDataSource.selectLanguage(languageCode)
		verify(settingsStore).selectLanguage(languageCode)
	}

	@Test
	fun testGetTextSize() = runTest {
		val textSize = 18
		whenever(settingsStore.getSelectedTextSize()).thenReturn(flowOf(textSize))

		val result = settingsDataSource.getSelectedTextSize().first()
		assertEquals(textSize, result)
	}

	@Test
	fun testSaveTextSize() = runTest {
		val textSize = 22
		settingsDataSource.selectTextSize(textSize)
		verify(settingsStore).selectTextSize(textSize)
	}

	@Test
	fun testGetEnableReadingHistory() = runTest {
		val enable = false
		whenever(settingsStore.checkIsReadingHistoryEnable()).thenReturn(flowOf(enable))

		val result = settingsDataSource.isReadingHistoryEnabled().first()
		assertEquals(enable, result)
	}

	@Test
	fun testSetEnableHistory() = runTest {
		val enable = false
		settingsDataSource.enableReadingHistory(enable)
		verify(settingsStore).enableReadingHistory(enable)
	}
}