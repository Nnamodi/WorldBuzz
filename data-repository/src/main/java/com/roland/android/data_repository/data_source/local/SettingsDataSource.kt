package com.roland.android.data_repository.data_source.local

import com.roland.android.domain.model.LanguageModel
import kotlinx.coroutines.flow.Flow

interface SettingsDataSource {

	suspend fun selectLanguage(languageCode: String)

	fun getSelectedLanguage(): Flow<LanguageModel>

	suspend fun selectTextSize(textSize: Int)

	fun getSelectedTextSize(): Flow<Int>

	suspend fun enableReadingHistory(enable: Boolean)

	fun isReadingHistoryEnabled(): Flow<Boolean>

}