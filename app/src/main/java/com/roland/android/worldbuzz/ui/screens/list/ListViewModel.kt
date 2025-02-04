package com.roland.android.worldbuzz.ui.screens.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.roland.android.domain.model.Article
import com.roland.android.domain.repository.SettingsRepository
import com.roland.android.domain.repository.UtilityRepository
import com.roland.android.domain.usecase.Collections
import com.roland.android.domain.usecase.GetNewsByCollectionUseCase
import com.roland.android.worldbuzz.data.ResponseConverter
import com.roland.android.worldbuzz.data.State
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ListViewModel : ViewModel(), KoinComponent {
	private val newsByCollectionUseCase by inject<GetNewsByCollectionUseCase>()
	private val settingsRepository by inject<SettingsRepository>()
	private val utilityRepository by inject<UtilityRepository>()
	private val converter by inject<ResponseConverter>()

	var articles: State<PagingData<Article>>? = null; private set
	private var selectedLanguage by mutableStateOf("")
	private var collectionDetails by mutableStateOf(CollectionDetails())

	private fun getSelectedLanguage() {
		viewModelScope.launch {
			settingsRepository.getSelectedLanguage().collect { language ->
				selectedLanguage = language.code
			}
		}
	}

	fun fetchNewsByCollection(details: CollectionDetails) {
		articles = null
		getSelectedLanguage()
		collectionDetails = details
		viewModelScope.launch {
			newsByCollectionUseCase.execute(
				GetNewsByCollectionUseCase.Request(
					collection = Collections.valueOf(details.collection),
					category = details.category,
					sourceName = details.sourceName,
					languageCode = selectedLanguage
				)
			)
				.map { converter.convertListData(it) }
				.collect { articles = it }
		}
	}

	fun clearHistory() {
		utilityRepository.clearReadingHistory()
		retry()
	}

	fun retry() = fetchNewsByCollection(collectionDetails)
}