package com.roland.android.worldbuzz.ui.screens.following

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roland.android.domain.model.CategoryModel
import com.roland.android.domain.model.SourceDetail
import com.roland.android.domain.repository.NewsRepository
import com.roland.android.domain.repository.UtilityRepository
import com.roland.android.worldbuzz.utils.Converters.update
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FollowingViewModel : ViewModel(), KoinComponent {
	private val newsRepository by inject<NewsRepository>()
	private val utilityRepository by inject<UtilityRepository>()

	var subscribedCategories = emptyList<CategoryModel>(); private set
	var subscribedSources = emptyList<SourceDetail>(); private set

	init {
		fetchSubscribedCategories()
		fetchSubscribedSources()
	}

	private fun fetchSubscribedCategories() {
		viewModelScope.launch {
			newsRepository.fetchSubscribedCategories().collect {
				subscribedCategories = it
			}
		}
	}

	private fun fetchSubscribedSources() {
		viewModelScope.launch {
			combine(
				newsRepository.fetchAllSources(),
				newsRepository.fetchSubscribedSources()
			) { newsSources, subscribedNewsSources ->
				val subscribed = subscribedNewsSources.map { it.id }
				subscribedSources = newsSources.filter { it.id in subscribed }
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
		val newCategories = if (subscribe) subscribedCategories + category else {
			subscribedCategories - category
		}
		utilityRepository.updateSubscribedCategories(newCategories)
	}

	private fun updateSubscribedSources(source: SourceDetail, subscribe: Boolean) {
		val sources = subscribedSources - source
		val updatedSource = source.update(subscribe)
		val newSources = sources + updatedSource
		utilityRepository.updateSubscribedSources(newSources)
	}
}