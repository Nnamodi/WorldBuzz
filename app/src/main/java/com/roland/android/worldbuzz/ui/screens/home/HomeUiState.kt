package com.roland.android.worldbuzz.ui.screens.home

import com.roland.android.domain.model.Article
import com.roland.android.worldbuzz.data.State

data class HomeUiState(
	val breakingNews: State<HomeModel>? = null
)

data class HomeModel(
	val trendingNews: List<Article>,
	val recommendedNews: List<Article>
)
