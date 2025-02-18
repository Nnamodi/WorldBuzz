package com.roland.android.worldbuzz.ui.screens.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roland.android.domain.model.SourceDetail
import com.roland.android.domain.repository.NewsRepository
import com.roland.android.domain.repository.SettingsRepository
import com.roland.android.domain.repository.UtilityRepository
import com.roland.android.domain.usecase.Collections
import com.roland.android.domain.usecase.GetNewsByCollectionUseCase
import com.roland.android.worldbuzz.data.ResponseConverter
import com.roland.android.worldbuzz.utils.Converters.refactor
import com.roland.android.worldbuzz.utils.Converters.toArticle
import com.roland.android.worldbuzz.utils.Converters.update
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DetailsViewModel : ViewModel(), KoinComponent {
	private val newsByCollectionUseCase by inject<GetNewsByCollectionUseCase>()
	private val newsRepository by inject<NewsRepository>()
	private val settingsRepository by inject<SettingsRepository>()
	private val utilityRepository by inject<UtilityRepository>()
	private val converter by inject<ResponseConverter>()

	private val _detailsUiState = MutableStateFlow(DetailsUiState())
	var detailsUiState by mutableStateOf(_detailsUiState.value); private set
	private var selectedLanguage by mutableStateOf("")
	private var subscribedSources = emptyList<SourceDetail>()
	private var allSources = emptyList<SourceDetail>()

	init {
		getTextSize()
		fetchSavedArticles()
		fetchSources()
		viewModelScope.launch {
			_detailsUiState.collect {
				detailsUiState = it
			}
		}
	}

	fun getArticle(articleJson: String) {
		val article = articleJson.toArticle()
		_detailsUiState.update { it.copy(article = article) }
		utilityRepository.addArticleToHistory(article)
		fetchNewsSourceDetails()
		fetchMoreFromSource()
	}

	private fun fetchSources() {
		viewModelScope.launch {
			newsRepository.fetchAllSources().collect { sources ->
				allSources = sources
				subscribedSources = sources.filter { it.subscribed }
			}
		}
	}

	private fun getTextSize() {
		viewModelScope.launch {
			settingsRepository.getSelectedTextSize().collect { size ->
				_detailsUiState.update { it.copy(textSize = size) }
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

	private fun fetchSavedArticles() {
		viewModelScope.launch {
			newsRepository.fetchSavedArticles().collect { articles ->
				_detailsUiState.update { it.copy(savedArticles = articles.refactor()) }
			}
		}
	}

	private fun fetchNewsSourceDetails() {
		viewModelScope.launch {
			val source = detailsUiState.article.source
			newsRepository.fetchSourceDetails(source).collect { sourceDetails ->
				_detailsUiState.update { it.copy(newsSource = sourceDetails) }
			}
		}
	}

	private fun fetchMoreFromSource() {
		getSelectedLanguage()
		viewModelScope.launch {
			val source = detailsUiState.article.source
			newsByCollectionUseCase.execute(
				GetNewsByCollectionUseCase.Request(
					collection = Collections.NewsBySource,
					sourceName = source.name,
					languageCode = selectedLanguage
				)
			)
				.map { converter.convertListData(it) }
				.collect { more ->
					_detailsUiState.update { it.copy(moreFromSource = more) }
				}
		}
	}

	fun actions(action: DetailsActions) {
		when (action) {
			DetailsActions.ReloadMoreFromSource -> reloadFromSource()
			is DetailsActions.SaveArticle -> saveArticle(action.save)
			is DetailsActions.SelectTextSize -> selectTextSize(action.textSize)
			DetailsActions.ShareArticle -> shareArticle()
			is DetailsActions.SubscribeToSource -> subscribeToSource(action.subscribe)
		}
	}

	private fun reloadFromSource() {
		_detailsUiState.update { it.copy(moreFromSource = null) }
		fetchMoreFromSource()
	}

	private fun saveArticle(save: Boolean) {
		if (save) {
			utilityRepository.saveArticle(detailsUiState.article)
		} else {
			utilityRepository.unsaveArticle(detailsUiState.article)
		}
		fetchSavedArticles()
	}

	private fun selectTextSize(size: Int) {
		settingsRepository.selectTextSize(size)
		getTextSize()
	}

	private fun shareArticle() {
		val url = detailsUiState.article.url
		utilityRepository.shareArticle(url)
	}

	private fun subscribeToSource(subscribe: Boolean) {
		val source = detailsUiState.newsSource
		val sources = subscribedSources - source
		val updatedSource = source.update(subscribe)
		val newSources = sources + updatedSource
		utilityRepository.updateSubscribedSources(newSources)
	}
}