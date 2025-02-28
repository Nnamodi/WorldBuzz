package com.roland.android.worldbuzz.ui.screens.search

import androidx.paging.PagingData
import com.roland.android.domain.model.Article
import com.roland.android.domain.model.CategoryModel
import com.roland.android.domain.model.SourceDetail
import com.roland.android.worldbuzz.data.State
import kotlinx.coroutines.flow.MutableStateFlow

data class SearchUiState(
	val query: String = "",
	val result: State<MutableStateFlow<PagingData<Article>>>? = null,
	val newsSources: List<SourceDetail> = emptyList(),
	val selectedCategory: CategoryModel? = null,
	val selectedSources: List<String> = emptyList()
)
