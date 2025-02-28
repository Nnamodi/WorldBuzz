package com.roland.android.worldbuzz.ui.screens.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roland.android.domain.model.CategoryModel
import com.roland.android.domain.model.Source
import com.roland.android.domain.repository.NewsRepository
import com.roland.android.domain.repository.SettingsRepository
import com.roland.android.domain.usecase.GetNewsUseCase
import com.roland.android.worldbuzz.data.ResponseConverter
import kotlinx.coroutines.flow.MutableStateFlow
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
	private var subscribedCategories = emptyList<CategoryModel>()
	private var subscribedSources = emptyList<Source>()
	private var selectedLanguage by mutableStateOf("")

	init {
		fetchSubscribedCategories()
		fetchSubscribedSources()
		fetchSelectedLanguage()
//		fetchNews()
		viewModelScope.launch {
			_homeUiState.collect { homeUiState = it }
		}
	}

	private fun fetchSubscribedCategories() {
		viewModelScope.launch {
			newsRepository.fetchSubscribedCategories().collect {
				subscribedCategories = it
				Log.i("HomeUiData", "Categories: $subscribedCategories")
			}
		}
	}

	private fun fetchSubscribedSources() {
		viewModelScope.launch {
			newsRepository.fetchSubscribedSources().collect {
				subscribedSources = it
				Log.i("HomeUiData", "Sources: $subscribedSources")
			}
		}
	}

	private fun fetchSelectedLanguage() {
		viewModelScope.launch {
			settingsRepository.getSelectedLanguage().collect {
				selectedLanguage = it.code
				Log.i("HomeUiData", "Language: $selectedLanguage")
			}
		}
	}

	private fun fetchNews() {
		viewModelScope.launch {
			val request = GetNewsUseCase.Request(
				categoryModels = subscribedCategories,
				sources = subscribedSources,
				languageCode = selectedLanguage
			)
			newsUseCase.execute(request)
				.map { converter.convertHomeData(it) }
				.collect { result ->
					_homeUiState.update { it.copy(breakingNews = result) }
					Log.i("HomeUiData", "$result")
				}
		}
	}

	fun retry() {
		_homeUiState.value = HomeUiState()
		fetchNews()
	}
}