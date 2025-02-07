package com.roland.android.worldbuzz.ui.screens.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roland.android.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SettingsViewModel : ViewModel(), KoinComponent {
	private val settingsRepository by inject<SettingsRepository>()

	private val _settingsUiState = MutableStateFlow(SettingsUiState())
	var settingsUiState by mutableStateOf(_settingsUiState.value); private set

	init {
		getSelectedLanguage()
		getSelectedTextSize()
		viewModelScope.launch {
			settingsRepository.isReadingHistoryEnabled().collect { enabledState ->
				_settingsUiState.update { it.copy(isReadingHistoryEnabled = enabledState) }
			}
		}
		viewModelScope.launch {
			_settingsUiState.collect {
				settingsUiState = it
			}
		}
	}

	private fun getSelectedLanguage() {
		viewModelScope.launch {
			settingsRepository.getSelectedLanguage().collect { language ->
				_settingsUiState.update { it.copy(selectedLanguage = language) }
			}
		}
	}

	private fun getSelectedTextSize() {
		viewModelScope.launch {
			settingsRepository.getSelectedTextSize().collect { textSize ->
				_settingsUiState.update { it.copy(selectedTextSize = textSize) }
			}
		}
	}

	fun actions(action: SettingsActions) {
		when (action) {
			is SettingsActions.SelectLanguage -> selectLanguage(action.languageCode)
			is SettingsActions.SelectTextSize -> selectTextSize(action.textSize)
			is SettingsActions.ToggleHistory -> toggleHistory(action.enable)
		}
	}

	private fun selectLanguage(languageCode: String) {
		settingsRepository.selectLanguage(languageCode)
		getSelectedLanguage()
	}

	private fun selectTextSize(textSize: Int) {
		settingsRepository.selectTextSize(textSize)
		getSelectedTextSize()
	}

	private fun toggleHistory(enable: Boolean) {
		viewModelScope.launch {
			settingsRepository.enableReadingHistory(enable).collect { enabledState ->
				_settingsUiState.update { it.copy(isReadingHistoryEnabled = enabledState) }
			}
		}
	}
}