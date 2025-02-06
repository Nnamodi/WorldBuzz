package com.roland.android.worldbuzz.ui.screens.search

data class SearchPref(
	val query: String = "",
	val categories: List<String> = emptyList(),
	val sources: List<String> = emptyList()
)