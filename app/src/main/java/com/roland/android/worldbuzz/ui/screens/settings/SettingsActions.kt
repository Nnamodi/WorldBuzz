package com.roland.android.worldbuzz.ui.screens.settings

sealed class SettingsActions {

	data class SelectLanguage(val languageCode: String) : SettingsActions()

	data class SelectTextSize(val textSize: Int) : SettingsActions()

	data class ToggleHistory(val enable: Boolean) : SettingsActions()

}