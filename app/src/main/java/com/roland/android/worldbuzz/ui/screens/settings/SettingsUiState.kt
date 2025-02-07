package com.roland.android.worldbuzz.ui.screens.settings

import com.roland.android.domain.model.LanguageModel

data class SettingsUiState(
	val selectedLanguage: LanguageModel = LanguageModel.English,
	val selectedTextSize: Int = 14,
	val isReadingHistoryEnabled: Boolean = true
)
