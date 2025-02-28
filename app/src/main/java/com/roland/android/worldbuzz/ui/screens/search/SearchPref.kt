package com.roland.android.worldbuzz.ui.screens.search

import com.roland.android.domain.model.CategoryModel

data class SearchPref(
	val query: String = "",
	val category: CategoryModel? = null,
	val sources: List<String> = emptyList()
)