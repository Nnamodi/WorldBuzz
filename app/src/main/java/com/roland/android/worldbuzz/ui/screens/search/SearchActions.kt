package com.roland.android.worldbuzz.ui.screens.search

sealed class SearchActions {

	data class Search(val pref: SearchPref) : SearchActions()

	data object Retry : SearchActions()

}