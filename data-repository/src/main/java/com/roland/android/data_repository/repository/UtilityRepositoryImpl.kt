package com.roland.android.data_repository.repository

import android.content.Context
import android.content.Intent
import com.roland.android.data_repository.R
import com.roland.android.data_repository.data_source.local.UtilityDataSource
import com.roland.android.domain.model.Article
import com.roland.android.domain.model.CategoryModel
import com.roland.android.domain.model.SourceDetail
import com.roland.android.domain.repository.UtilityRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class UtilityRepositoryImpl(
	private val utilityDataSource: UtilityDataSource,
	private val coroutineScope: CoroutineScope,
	private val context: Context
) : UtilityRepository {

	override fun updateSubscribedSources(sources: List<SourceDetail>) {
		coroutineScope.launch {
			utilityDataSource.updateSubscribedSources(sources)
		}
	}

	override fun updateSubscribedCategories(categories: List<CategoryModel>) {
		coroutineScope.launch {
			utilityDataSource.updateSubscribedCategories(categories)
		}
	}

	override fun saveArticle(article: Article) {
		coroutineScope.launch {
			utilityDataSource.saveArticle(article)
		}
	}

	override fun unsaveArticle(article: Article) {
		coroutineScope.launch {
			utilityDataSource.unsaveArticle(article)
		}
	}

	override fun addArticleToHistory(article: Article) {
		coroutineScope.launch {
			utilityDataSource.addArticleToHistory(article)
		}
	}

	override fun clearReadingHistory() {
		coroutineScope.launch {
			utilityDataSource.clearReadingHistory()
		}
	}

	override fun shareArticle(url: String) {
		Intent(Intent.ACTION_SEND).apply {
			type = "text/plain"
			putExtra(Intent.EXTRA_TEXT, url)
		}.also { intent ->
			val chooserIntent = Intent.createChooser(
				intent, context.getString(R.string.chooser_title)
			)
			context.startActivity(chooserIntent)
		}
	}
}