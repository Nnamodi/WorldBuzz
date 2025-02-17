package com.roland.android.worldbuzz.ui.screens.discover

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roland.android.domain.repository.SettingsRepository
import com.roland.android.domain.usecase.CategorySet
import com.roland.android.domain.usecase.GetNewsByCategoryUseCase
import com.roland.android.worldbuzz.data.ResponseConverter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DiscoverViewModel : ViewModel(), KoinComponent {
	private val newsByCategoryUseCase by inject<GetNewsByCategoryUseCase>()
	private val settingsRepository by inject<SettingsRepository>()
	private val converter by inject<ResponseConverter>()

	private val _discoverUiState = MutableStateFlow(DiscoverUiState())
	var discoverUiState by mutableStateOf(_discoverUiState.value); private set
	private var selectedLanguage by mutableStateOf("")

	init {
		fetchNewsI()
		fetchNewsII()
		viewModelScope.launch {
			_discoverUiState.collect {
				discoverUiState = it
				Log.i("DiscoverUiData", "$it")
			}
		}
	}

	private fun fetchNewsI() {
		getSelectedLanguage()
		viewModelScope.launch {
			newsByCategoryUseCase.execute(
				GetNewsByCategoryUseCase.Request(
					categorySet = CategorySet.First,
					languageCode = selectedLanguage
				)
			)
				.map { converter.convertDiscoverDataI(it) }
				.collect { news ->
					_discoverUiState.update {
						it.copy(newsI = news)
					}
				}
		}
	}

	private fun fetchNewsII() {
		viewModelScope.launch {
			newsByCategoryUseCase.execute(
				GetNewsByCategoryUseCase.Request(
					categorySet = CategorySet.Second,
					languageCode = selectedLanguage
				)
			)
				.map { converter.convertDiscoverDataII(it) }
				.collect { news ->
					_discoverUiState.update {
						it.copy(newsII = news)
					}
				}
		}
	}

	private fun getSelectedLanguage() {
		viewModelScope.launch {
			settingsRepository.getSelectedLanguage().collect { language ->
				selectedLanguage = language.code
			}
		}
	}

	fun retry() {
		_discoverUiState.value = DiscoverUiState()
		fetchNewsI()
		fetchNewsII()
	}
}