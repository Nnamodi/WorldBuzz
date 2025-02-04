package com.roland.android.worldbuzz.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roland.android.domain.repository.NewsRepository
import com.roland.android.domain.repository.SettingsRepository
import com.roland.android.domain.usecase.GetNewsUseCase
import com.roland.android.domain.util.Constant.SEPARATOR
import com.roland.android.worldbuzz.data.ResponseConverter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomeViewModel : ViewModel(), KoinComponent {
	private val newsUseCase by inject<GetNewsUseCase>()
	private val newsRepository by inject<NewsRepository>()
	private val settingsRepository by inject<SettingsRepository>()
	private val converter by inject<ResponseConverter>()

	private val _homeUiState = MutableStateFlow(HomeUiState())
	var homeUiState by mutableStateOf(_homeUiState.value); private set

	init {
		fetchNews()
		viewModelScope.launch {
			_homeUiState.collect { homeUiState = it }
		}
	}

	private fun fetchNews() {
		viewModelScope.launch {
			combine(
				newsRepository.fetchSubscribedCategories(),
				newsRepository.fetchSubscribedSources(),
				settingsRepository.getSelectedLanguage()
			) { categories, sources, language ->
				val request = GetNewsUseCase.Request(
					categoryModels = categories,
					sources = sources.map { it.name }.joinToString { SEPARATOR },
					languageCode = language.code
				)
				newsUseCase.execute(request)
					.map { converter.convertHomeData(it) }
					.collect { result ->
						_homeUiState.update { it.copy(breakingNews = result) }
					}
			}
		}
	}

	fun retry() {
		_homeUiState.value = HomeUiState()
		fetchNews()
	}
}