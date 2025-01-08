package com.roland.android.domain.repository

import com.roland.android.domain.model.LanguageModel
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

	fun selectLanguage(languageCode: String)

	fun getSelectedLanguage(): Flow<LanguageModel>

	fun selectTextSize(textSize: Int)

	fun getSelectedTextSize(): Flow<Int>

	fun enableReadingHistory(enable: Boolean): Flow<Boolean>

}