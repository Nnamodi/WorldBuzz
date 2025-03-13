package com.roland.android.worldbuzz.ui.screens.following

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roland.android.domain.model.CategoryModel
import com.roland.android.domain.model.SourceDetail
import com.roland.android.domain.repository.NewsRepository
import com.roland.android.domain.repository.UtilityRepository
import com.roland.android.worldbuzz.utils.Converters.update
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FollowingViewModel : ViewModel(), KoinComponent {
	private val newsRepository by inject<NewsRepository>()
	private val utilityRepository by inject<UtilityRepository>()

	private val _categoriesUiState = MutableStateFlow(CategoriesUiState())
	var categoriesUiState by mutableStateOf(_categoriesUiState.value); private set

	private val _sourcesUiState = MutableStateFlow(SourcesUiState())
	var sourcesUiState by mutableStateOf(_sourcesUiState.value); private set

	init {
		fetchSubscribedCategories()
		fetchAllSources()
		viewModelScope.launch {
			_categoriesUiState.collect {
				categoriesUiState = it
			}
		}
		viewModelScope.launch {
			_sourcesUiState.collect {
				sourcesUiState = it
			}
		}
	}

	private fun fetchSubscribedCategories() {
		viewModelScope.launch {
			newsRepository.fetchSubscribedCategories().collect { categories ->
				_categoriesUiState.update { it.copy(subscribedCategories = categories) }
			}
		}
	}

	private fun fetchAllSources() {
		viewModelScope.launch {
			newsRepository.fetchAllSources().collect { sources ->
				_sourcesUiState.update { it.copy(allSources = sources) }
			}
		}
	}

	fun actions(action: FollowingActions) {
		when (action) {
			is FollowingActions.SubscribedToCategory -> updateSubscribedCategories(action.category, action.subscribe)
			is FollowingActions.SubscribedToSource -> updateSubscribedSources(action.source, action.subscribe)
		}
	}

	private fun updateSubscribedCategories(category: CategoryModel, subscribe: Boolean) {
		val newCategories = if (subscribe) categoriesUiState.subscribedCategories + category else {
			categoriesUiState.subscribedCategories - category
		}
		utilityRepository.updateSubscribedCategories(newCategories)
		fetchSubscribedCategories()
	}

	private fun updateSubscribedSources(source: SourceDetail, subscribe: Boolean) {
		val sources = sourcesUiState.allSources - source
		val updatedSource = source.update(subscribe)
		val newSources = sources + updatedSource
		utilityRepository.updateSubscribedSources(newSources)
	}
}