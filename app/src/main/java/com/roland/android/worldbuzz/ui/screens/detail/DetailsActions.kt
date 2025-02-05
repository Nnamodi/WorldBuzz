package com.roland.android.worldbuzz.ui.screens.detail

sealed class DetailsActions {

	data object ReloadMoreFromSource : DetailsActions()

	data class SaveArticle(val save: Boolean) : DetailsActions()

	data class SelectTextSize(val textSize: Int) : DetailsActions()

	data object ShareArticle : DetailsActions()

	data class SubscribeToSource(val subscribe: Boolean) : DetailsActions()

}