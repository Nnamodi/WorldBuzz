package com.roland.android.worldbuzz.ui.screens.discover

import androidx.paging.PagingData
import com.roland.android.domain.model.Article
import com.roland.android.worldbuzz.data.State

data class DiscoverUiState(
	val allNews: State<PagingData<Article>>? = null,
	val businessNews: State<PagingData<Article>>? = null,
	val entertainmentNews: State<PagingData<Article>>? = null,
	val healthNews: State<PagingData<Article>>? = null,
	val scienceNews: State<PagingData<Article>>? = null,
	val sportsNews: State<PagingData<Article>>? = null,
	val techNews: State<PagingData<Article>>? = null
)
