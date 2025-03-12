package com.roland.android.worldbuzz.ui.screens.search

import android.util.Log
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
		fetchAllSources()
		viewModelScope.launch {
			_searchUiState.collect {
				searchUiState = it
			}
		}
	}

	private fun fetchAllSources() {
		viewModelScope.launch {
			newsRepository.fetchAllSources().collect { sources ->
				_searchUiState.update { state ->
					state.copy(
						newsSources = sources
							.sortedBy { it.subscribed }
							.take(12)
					)
				}
				Log.i("SearchDataInfo", "$sources")
			}
		}
	}

	private fun fetchSelectedLanguage() {
		viewModelScope.launch {
			settingsRepository.getSelectedLanguage().collect {
				selectedLanguage = it.code
				Log.i("SearchDataInfo", selectedLanguage)
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
		fetchSelectedLanguage()
		_searchUiState.update { it.copy(
			query = pref.query,
			result = null,
			selectedCategory = pref.category,
			selectedSources = pref.sources
		) }
		searchPref = pref
		viewModelScope.launch {
			searchUseCase.execute(
				GetNewsBySearchUseCase.Request(
					query = pref.query,
					category = pref.category?.category ?: "",
					sources = pref.sources
				)
			)
				.map { converter.convertSearchData(it) }
				.collect { articles ->
					_searchUiState.update { it.copy(result = articles) }
				}
		}
	}
}