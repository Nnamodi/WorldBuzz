package com.roland.android.worldbuzz.ui.screens.discover

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.roland.android.domain.model.Article
import com.roland.android.domain.model.CategoryModel
import com.roland.android.domain.repository.SettingsRepository
import com.roland.android.domain.usecase.Collections
import com.roland.android.domain.usecase.GetNewsByCollectionUseCase
import com.roland.android.worldbuzz.data.ResponseConverter
import com.roland.android.worldbuzz.data.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
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
		fetchCategorizedNewsI()
		fetchCategorizedNewsII()
		viewModelScope.launch {
			_discoverUiState.collect {
				discoverUiState = it
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

	private fun fetchNewsByCategory(category: String): Flow<State<PagingData<Article>>> {
		getSelectedLanguage()
		return newsByCollectionUseCase.execute(
			GetNewsByCollectionUseCase.Request(
				collection = Collections.NewsByCategory,
				category = category,
				languageCode = selectedLanguage
			)
		).map { converter.convertListData(it) }
	}

	private fun fetchCategorizedNewsI() {
		val categories = CategoryModel.entries
		viewModelScope.launch {
			combine(
				fetchNewsByCategory(categories[0].category),
				fetchNewsByCategory(categories[1].category),
				fetchNewsByCategory(categories[2].category),
				fetchNewsByCategory(categories[3].category)
			) { all, business, entertainment, health ->
				_discoverUiState.update {
					it.copy(
						allNews = all,
						businessNews = business,
						entertainmentNews = entertainment,
						healthNews = health
					)
				}
			}
		}
	}

	private fun fetchCategorizedNewsII() {
		val categories = CategoryModel.entries
		viewModelScope.launch {
			combine(
				fetchNewsByCategory(categories[4].category),
				fetchNewsByCategory(categories[5].category),
				fetchNewsByCategory(categories[6].category)
			) { science, sports, tech ->
				_discoverUiState.update {
					it.copy(
						scienceNews = science,
						sportsNews = sports,
						techNews = tech
					)
				}
			}
		}
	}

	fun retry() {
		_discoverUiState.value = DiscoverUiState()
		fetchCategorizedNewsI()
		fetchCategorizedNewsII()
	}
}