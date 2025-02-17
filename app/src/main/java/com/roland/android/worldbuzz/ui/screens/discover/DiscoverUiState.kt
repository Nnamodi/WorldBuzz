package com.roland.android.worldbuzz.ui.screens.discover

import androidx.paging.PagingData
import com.roland.android.domain.model.Article
import com.roland.android.worldbuzz.data.State

data class DiscoverUiState(
	val newsI: State<DiscoverModelI>? = null,
	val newsII: State<DiscoverModelII>? = null
)

data class DiscoverModelI(
	val allNews: PagingData<Article>,
	val businessNews: PagingData<Article>,
	val entertainmentNews: PagingData<Article>,
	val healthNews: PagingData<Article>
)

data class DiscoverModelII(
	val scienceNews: PagingData<Article>,
	val sportsNews: PagingData<Article>,
	val techNews: PagingData<Article>
)
