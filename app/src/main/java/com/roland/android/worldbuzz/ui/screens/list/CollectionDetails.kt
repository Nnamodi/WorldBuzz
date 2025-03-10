package com.roland.android.worldbuzz.ui.screens.list

import android.content.Context
import com.roland.android.domain.usecase.Collections
import com.roland.android.worldbuzz.R
import com.roland.android.worldbuzz.utils.Converters.capitalizeFirstLetter

data class CollectionDetails(
	val collection: String = "",
	val category: String? = null,
	val sourceName: String? = null
) {
	fun getTitle(context: Context): String {
		val collection = Collections.valueOf(collection)
		val genericTitle = context.getString(R.string.news)
		val title = when (collection) {
			Collections.TrendingNews -> context.getString(R.string.trending)
			Collections.RecommendedNews -> context.getString(R.string.recommended)
			Collections.NewsByCategory -> category?.capitalizeFirstLetter()
			Collections.NewsBySource -> sourceName?.capitalizeFirstLetter()
			Collections.ReadingHistory -> context.getString(R.string.reading_history)
			Collections.SavedArticles -> context.getString(R.string.saved_articles)
			else -> genericTitle
		}
		return title ?: genericTitle
	}
}