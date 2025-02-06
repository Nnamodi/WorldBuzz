package com.roland.android.worldbuzz.ui.screens.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.roland.android.domain.model.Article
import com.roland.android.domain.repository.SettingsRepository
import com.roland.android.domain.usecase.GetNewsBySearchUseCase
import com.roland.android.worldbuzz.data.ResponseConverter
import com.roland.android.worldbuzz.data.State
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SearchViewModel : ViewModel(), KoinComponent {
	private val searchUseCase by inject<GetNewsBySearchUseCase>()
	private val settingsRepository by inject<SettingsRepository>()
	private val converter by inject<ResponseConverter>()

	var searchArticles: State<PagingData<Article>>? = null; private set
	private var selectedLanguage by mutableStateOf("")
	private var searchPref by mutableStateOf(SearchPref())

	private fun getSelectedLanguage() {
		viewModelScope.launch {
			settingsRepository.getSelectedLanguage().collect { language ->
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
		getSelectedLanguage()
		searchArticles = null
		searchPref = pref
		viewModelScope.launch {
			searchUseCase.execute(
				GetNewsBySearchUseCase.Request(
					query = pref.query,
					categories = pref.categories,
					sources = pref.sources,
					languageCode = selectedLanguage
				)
			)
				.map { converter.convertSearchData(it) }
				.collect { searchArticles = it }
		}
	}
}