package com.roland.android.data_local.data_source

import com.roland.android.data_local.datastore.SettingsStore
import com.roland.android.data_repository.data_source.local.SettingsDataSource
import com.roland.android.domain.model.LanguageModel
import kotlinx.coroutines.flow.Flow

class SettingsDataSourceImpl(
	private val settingsStore: SettingsStore
) : SettingsDataSource {
	override suspend fun selectLanguage(languageCode: String) {
		settingsStore.selectLanguage(languageCode)
	}

	override fun getSelectedLanguage(): Flow<LanguageModel> {
		return settingsStore.getSelectedLanguage()
	}

	override suspend fun selectTextSize(textSize: Int) {
		settingsStore.selectTextSize(textSize)
	}

	override fun getSelectedTextSize(): Flow<Int> {
		return settingsStore.getSelectedTextSize()
	}

	override suspend fun enableReadingHistory(enable: Boolean) {
		settingsStore.checkIsReadingHistoryEnable()
	}

	override fun isReadingHistoryEnabled(): Flow<Boolean> {
		return settingsStore.checkIsReadingHistoryEnable()
	}
}