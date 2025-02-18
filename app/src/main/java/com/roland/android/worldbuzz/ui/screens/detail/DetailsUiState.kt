package com.roland.android.worldbuzz.ui.screens.detail

import androidx.paging.PagingData
import com.roland.android.domain.model.Article
import com.roland.android.domain.model.SourceDetail
import com.roland.android.worldbuzz.data.State
import kotlinx.coroutines.flow.MutableStateFlow

data class DetailsUiState(
	val article: Article = Article(),
	val newsSource: SourceDetail = SourceDetail(),
	val textSize: Int = 14,
	val savedArticles: MutableStateFlow<PagingData<Article>> = MutableStateFlow(PagingData.empty()),
	val moreFromSource: State<MutableStateFlow<PagingData<Article>>>? = null
)
