package com.roland.android.worldbuzz.ui.screens.discover

import androidx.paging.PagingData
import com.roland.android.domain.model.Article
import com.roland.android.worldbuzz.data.State
import kotlinx.coroutines.flow.MutableStateFlow

data class DiscoverUiState(
	val newsI: State<DiscoverModelI>? = null,
	val newsII: State<DiscoverModelII>? = null
)

data class DiscoverModelI(
	val allNews: MutableStateFlow<PagingData<Article>>,
	val businessNews: MutableStateFlow<PagingData<Article>>,
	val entertainmentNews: MutableStateFlow<PagingData<Article>>,
	val healthNews: MutableStateFlow<PagingData<Article>>
)

data class DiscoverModelII(
	val scienceNews: MutableStateFlow<PagingData<Article>>,
	val sportsNews: MutableStateFlow<PagingData<Article>>,
	val techNews: MutableStateFlow<PagingData<Article>>
)
