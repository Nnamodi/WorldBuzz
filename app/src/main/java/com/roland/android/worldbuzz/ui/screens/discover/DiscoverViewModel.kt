package com.roland.android.worldbuzz.ui.screens.discover

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roland.android.domain.model.CategoryModel
import com.roland.android.domain.repository.SettingsRepository
import com.roland.android.domain.usecase.Collections
import com.roland.android.domain.usecase.GetNewsByCollectionUseCase
import com.roland.android.worldbuzz.data.ResponseConverter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DiscoverViewModel : ViewModel(), KoinComponent {
	private val newsByCollectionUseCase by inject<GetNewsByCollectionUseCase>()
	private val settingsRepository by inject<SettingsRepository>()
	private val converter by inject<ResponseConverter>()

	private val _discoverUiState = MutableStateFlow(DiscoverUiState())
	var discoverUiState by mutableStateOf(_discoverUiState.value); private set
	private var selectedLanguage by mutableStateOf("")

	init {
		fetchAllNews()
		fetchBusinessNews()
		fetchEntertainmentNews()
		fetchHealthNews()
		fetchScienceNews()
		fetchSportsNews()
		fetchTechNews()
		viewModelScope.launch {
			_discoverUiState.collect {
				discoverUiState = it
				Log.i("DiscoverUiData", "$it")
			}
		}
	}

	private fun fetchAllNews() {
		getSelectedLanguage()
		viewModelScope.launch {
			newsByCollectionUseCase.execute(
				GetNewsByCollectionUseCase.Request(
					collection = Collections.NewsByCategory,
					category = CategoryModel.All.category,
					languageCode = selectedLanguage
				)
			)
				.map { converter.convertListData(it) }
				.collect { news ->
					_discoverUiState.update {
						it.copy(allNews = news)
					}
				}
		}
	}

	private fun fetchBusinessNews() {
		viewModelScope.launch {
			newsByCollectionUseCase.execute(
				GetNewsByCollectionUseCase.Request(
					collection = Collections.NewsByCategory,
					category = CategoryModel.Business.category,
					languageCode = selectedLanguage
				)
			)
				.map { converter.convertListData(it) }
				.collect { news ->
					_discoverUiState.update {
						it.copy(businessNews = news)
					}
				}
		}
	}

	private fun fetchEntertainmentNews() {
		viewModelScope.launch {
			newsByCollectionUseCase.execute(
				GetNewsByCollectionUseCase.Request(
					collection = Collections.NewsByCategory,
					category = CategoryModel.Entertainment.category,
					languageCode = selectedLanguage
				)
			)
				.map { converter.convertListData(it) }
				.collect { news ->
					_discoverUiState.update {
						it.copy(entertainmentNews = news)
					}
				}
		}
	}

	private fun fetchHealthNews() {
		viewModelScope.launch {
			newsByCollectionUseCase.execute(
				GetNewsByCollectionUseCase.Request(
					collection = Collections.NewsByCategory,
					category = CategoryModel.Health.category,
					languageCode = selectedLanguage
				)
			)
				.map { converter.convertListData(it) }
				.collect { news ->
					_discoverUiState.update {
						it.copy(healthNews = news)
					}
				}
		}
	}

	private fun fetchScienceNews() {
		viewModelScope.launch {
			newsByCollectionUseCase.execute(
				GetNewsByCollectionUseCase.Request(
					collection = Collections.NewsByCategory,
					category = CategoryModel.Science.category,
					languageCode = selectedLanguage
				)
			)
				.map { converter.convertListData(it) }
				.collect { news ->
					_discoverUiState.update {
						it.copy(scienceNews = news)
					}
				}
		}
	}

	private fun fetchSportsNews() {
		viewModelScope.launch {
			newsByCollectionUseCase.execute(
				GetNewsByCollectionUseCase.Request(
					collection = Collections.NewsByCategory,
					category = CategoryModel.Sports.category,
					languageCode = selectedLanguage
				)
			)
				.map { converter.convertListData(it) }
				.collect { news ->
					_discoverUiState.update {
						it.copy(sportsNews = news)
					}
				}
		}
	}

	private fun fetchTechNews() {
		viewModelScope.launch {
			newsByCollectionUseCase.execute(
				GetNewsByCollectionUseCase.Request(
					collection = Collections.NewsByCategory,
					category = CategoryModel.Technology.category,
					languageCode = selectedLanguage
				)
			)
				.map { converter.convertListData(it) }
				.collect { news ->
					_discoverUiState.update {
						it.copy(techNews = news)
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
		fetchAllNews()
		fetchBusinessNews()
		fetchEntertainmentNews()
		fetchHealthNews()
		fetchScienceNews()
		fetchSportsNews()
		fetchTechNews()
	}
}