package com.roland.android.worldbuzz.ui.screens.discover

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Tune
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.roland.android.domain.model.Article
import com.roland.android.domain.model.CategoryModel
import com.roland.android.domain.usecase.Collections
import com.roland.android.worldbuzz.R
import com.roland.android.worldbuzz.data.State
import com.roland.android.worldbuzz.data.sampleNewsPagingData
import com.roland.android.worldbuzz.ui.components.appbars.TopAppBar
import com.roland.android.worldbuzz.ui.components.widgets.ArticleJson
import com.roland.android.worldbuzz.ui.components.widgets.ListItems
import com.roland.android.worldbuzz.ui.components.widgets.Snackbar
import com.roland.android.worldbuzz.ui.components.widgets.SnackbarDuration
import com.roland.android.worldbuzz.ui.navigation.Screens
import com.roland.android.worldbuzz.ui.screens.CommonScaffold
import com.roland.android.worldbuzz.ui.screens.CommonScreen
import com.roland.android.worldbuzz.ui.screens.list.ListLoadingUi
import com.roland.android.worldbuzz.utils.Constants.NavigationBarHeight
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@Composable
fun DiscoverScreen(
	uiState: DiscoverUiState,
	retry: () -> Unit,
	navigate: (Screens) -> Unit
) {
	val errorMessage = rememberSaveable { mutableStateOf<String?>(null) }

	CommonScaffold(
		topBar = {
			TopAppBar(
				title = stringResource(R.string.discover),
				canGoBack = false
			)
		}
	) { paddingValues ->
		Column(Modifier.padding(paddingValues)) {
			Row(
				modifier = Modifier
					.fillMaxWidth()
					.padding(24.dp, 16.dp)
					.clip(CircleShape)
					.clickable { navigate(Screens.SearchScreen) }
					.background(MaterialTheme.colorScheme.surfaceContainerLow)
					.padding(12.dp)
					.alpha(0.5f),
				verticalAlignment = Alignment.CenterVertically
			) {
				Icon(Icons.Rounded.Search, null)
				Text(
					text = stringResource(R.string.search_placeholder),
					modifier = Modifier
						.weight(1f)
						.padding(horizontal = 10.dp)
				)
				Icon(Icons.Rounded.Tune, null)
			}

			NewsPager(
				state = uiState.newsI,
				state2 = uiState.newsII,
				onItemClick = { navigate(Screens.DetailsScreen(it)) },
				onLoadError = { errorMessage.value = it }
			)
		}

		if (errorMessage.value != null) {
			Snackbar(
				message = errorMessage.value!!,
				paddingValues = paddingValues,
				modifier = Modifier.padding(bottom = NavigationBarHeight),
				actionLabel = stringResource(R.string.retry),
				action = retry,
				duration = SnackbarDuration.Indefinite
			)
		}

		LaunchedEffect(Unit) {
			if (uiState.newsI is State.Error || uiState.newsII is State.Error) return@LaunchedEffect
			errorMessage.value = null
		}
	}
}

@Composable
private fun NewsPager(
	state: State<DiscoverModelI>?,
	state2: State<DiscoverModelII>?,
	onItemClick: (ArticleJson) -> Unit,
	onLoadError: (String?) -> Unit
) {
	val scope = rememberCoroutineScope()
	val pagerState = rememberPagerState { CategoryModel.entries.size }

	CategoryTabRows(
		currentPage = pagerState.currentPage,
		onTabClicked = {
			scope.launch { pagerState.animateScrollToPage(it) }
		}
	)

	HorizontalPager(pagerState) { page ->
		CommonScreen(
			state = state,
			state2 = state2,
			loadingScreen = { error ->
				ListLoadingUi(isLoading = error == null)
				onLoadError(error)
			}
		) { modelI, modelII ->
			val articles = page.articles(modelI, modelII)

			ListItems(
				articles = articles.collectAsLazyPagingItems(),
				collection = Collections.NewsByCategory.name,
				onItemClick = onItemClick,
				onLoadError = onLoadError
			)
		}
	}
}

@Composable
private fun CategoryTabRows(
	currentPage: Int,
	onTabClicked: (Int) -> Unit
) {
	ScrollableTabRow(
		selectedTabIndex = currentPage,
		edgePadding = 0.dp
	) {
		CategoryModel.entries.forEachIndexed { index, categoryModel ->
			val selected = currentPage == index
			Tab(
				text = { Text(categoryModel.name) },
				selected = selected,
				onClick = { onTabClicked(index) },
				modifier = Modifier.alpha(if (selected) 1f else 0.5f),
				unselectedContentColor = MaterialTheme.colorScheme.outline
			)
		}
	}
}

private fun Int.articles(
	modelI: DiscoverModelI,
	modelII: DiscoverModelII
): MutableStateFlow<PagingData<Article>> {
	return when (this) {
		0 -> modelI.allNews
		1 -> modelI.businessNews
		2 -> modelI.entertainmentNews
		3 -> modelI.healthNews
		4 -> modelII.scienceNews
		5 -> modelII.sportsNews
		else -> modelII.techNews
	}
}

@Preview
@Composable
private fun DiscoverScreenPreview() {
	MaterialTheme {
		val uiState = DiscoverUiState(
			newsI = State.Success(DiscoverModelI(sampleNewsPagingData, sampleNewsPagingData, sampleNewsPagingData, sampleNewsPagingData)),
			newsII = State.Success(DiscoverModelII(sampleNewsPagingData, sampleNewsPagingData, sampleNewsPagingData))
		)

		DiscoverScreen(
			uiState = uiState,
			retry = {},
			navigate = {}
		)
	}
}