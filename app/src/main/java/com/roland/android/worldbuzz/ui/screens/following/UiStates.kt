package com.roland.android.worldbuzz.ui.screens.following

import com.roland.android.domain.model.CategoryModel
import com.roland.android.domain.model.SourceDetail

data class CategoriesUiState(
	val subscribedCategories: List<CategoryModel> = emptyList()
)

data class SourcesUiState(
	val allSources: List<SourceDetail> = emptyList()
)
