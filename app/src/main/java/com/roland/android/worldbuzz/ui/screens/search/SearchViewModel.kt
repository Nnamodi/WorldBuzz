package com.roland.android.worldbuzz.ui.screens.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roland.android.domain.repository.NewsRepository
import com.roland.android.domain.repository.SettingsRepository
import com.roland.android.domain.usecase.GetNewsBySearchUseCase
import com.roland.android.worldbuzz.data.ResponseConverter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SearchViewModel : ViewModel(), KoinComponent {
	private val searchUseCase by inject<GetNewsBySearchUseCase>()
	private val newsRepository by inject<NewsRepository>()
	private val settingsRepository by inject<SettingsRepository>()
	private val converter by inject<ResponseConverter>()

	private val _searchUiState = MutableStateFlow(SearchUiState())
	var searchUiState by mutableStateOf(_searchUiState.value); private set
	private var selectedLanguage by mutableStateOf("")
	private var searchPref by mutableStateOf(SearchPref())

	init {
		viewModelScope.launch {
			_searchUiState.collect {
				searchUiState = it
			}
		}
	}

	private fun fetchPrefs() {
		viewModelScope.launch {
			combine(
				newsRepository.fetchSubscribedSources(),
				newsRepository.fetchAllSources(),
				settingsRepository.getSelectedLanguage()
			) { sources, allSources, language ->
				_searchUiState.update { state ->
					val subscribedSources = allSources.filter { sourceDetail ->
						sourceDetail.name in sources.map { it.name }
					}
					state.copy(
						newsSources = (subscribedSources + allSources).take(12)
					)
				}
				selectedLanguage = language.code
			}
		}
	}

	fun actions(action: SearchActions) {
		when (action) {
			is SearchActions.Search -> onSearch(action.pref)
			SearchActions.Retry -> onSearch(searchPref)
		}
	}

	private fun onSearch(pref: SearchPref) {
		fetchPrefs()
		_searchUiState.update { it.copy(
			query = pref.query,
			result = null,
			selectedCategories = pref.categories,
			selectedSources = pref.sources
		) }
		searchPref = pref
		viewModelScope.launch {
			searchUseCase.execute(
				GetNewsBySearchUseCase.Request(
					query = pref.query,
					categories = pref.categories.map { it.category },
					sources = pref.sources,
					languageCode = selectedLanguage
				)
			)
				.map { converter.convertSearchData(it) }
				.collect { articles ->
					_searchUiState.update { it.copy(result = articles) }
				}
		}
	}
}