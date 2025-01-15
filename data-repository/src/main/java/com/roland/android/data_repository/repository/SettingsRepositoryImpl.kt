package com.roland.android.data_repository.repository

import com.roland.android.data_repository.data_source.local.SettingsDataSource
import com.roland.android.domain.model.LanguageModel
import com.roland.android.domain.repository.SettingsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SettingsRepositoryImpl : SettingsRepository, KoinComponent {
	private val settingsDataSource by inject<SettingsDataSource>()
	private val coroutineScope by inject<CoroutineScope>()

	override fun selectLanguage(languageCode: String) {
		coroutineScope.launch {
			settingsDataSource.selectLanguage(languageCode)
		}
	}

	override fun getSelectedLanguage(): Flow<LanguageModel> {
		return settingsDataSource.getSelectedLanguage()
	}

	override fun selectTextSize(textSize: Int) {
		coroutineScope.launch {
			settingsDataSource.selectTextSize(textSize)
		}
	}

	override fun getSelectedTextSize(): Flow<Int> {
		return settingsDataSource.getSelectedTextSize()
	}

	override fun enableReadingHistory(enable: Boolean): Flow<Boolean> {
		coroutineScope.launch {
			settingsDataSource.enableReadingHistory(enable)
		}
		return settingsDataSource.isReadingHistoryEnabled()
	}
}