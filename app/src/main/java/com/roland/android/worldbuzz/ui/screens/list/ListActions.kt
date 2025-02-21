package com.roland.android.worldbuzz.ui.screens.list

sealed class ListActions {

	data object ClearHistory : ListActions()

	data object Retry : ListActions()

}