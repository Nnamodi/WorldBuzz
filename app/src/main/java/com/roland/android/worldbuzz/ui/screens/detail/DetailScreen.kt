package com.roland.android.worldbuzz.ui.screens.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import com.roland.android.worldbuzz.ui.components.appbars.DetailsTopAppBar
import com.roland.android.worldbuzz.ui.navigation.Screens

@Composable
fun DetailsScreen(
	uiState: DetailsUiState,
	actions: (DetailsActions) -> Unit,
	navigate: (Screens) -> Unit
) {
	val (article, newsSource, textSize, saved, moreFromSource) = uiState
	val savedArticles = saved.collectAsLazyPagingItems().itemSnapshotList.items
	val articleSaved by rememberSaveable {
		derivedStateOf { article in savedArticles }
	}

	Scaffold(
		topBar = {
			DetailsTopAppBar(
				article = article,
				articleSaved = articleSaved,
				saveArticle = { actions(DetailsActions.SaveArticle(!articleSaved)) },
				seeMore = {},
				navigate = navigate
			)
		}
	) { paddingValues ->
		Column(Modifier.padding(paddingValues)) {
			Text(text = article.content)
		}
	}
}