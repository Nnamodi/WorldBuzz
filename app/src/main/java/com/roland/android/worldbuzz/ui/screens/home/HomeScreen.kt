package com.roland.android.worldbuzz.ui.screens.home

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.roland.android.domain.model.Article
import com.roland.android.domain.usecase.Collections
import com.roland.android.worldbuzz.R
import com.roland.android.worldbuzz.data.State
import com.roland.android.worldbuzz.data.sampleNewsData
import com.roland.android.worldbuzz.ui.components.PagerPoster
import com.roland.android.worldbuzz.ui.components.appbars.TopAppBar
import com.roland.android.worldbuzz.ui.components.widgets.ArticleJson
import com.roland.android.worldbuzz.ui.components.widgets.Header
import com.roland.android.worldbuzz.ui.components.widgets.ListItem
import com.roland.android.worldbuzz.ui.components.widgets.Snackbar
import com.roland.android.worldbuzz.ui.components.widgets.SnackbarDuration
import com.roland.android.worldbuzz.ui.navigation.Screens
import com.roland.android.worldbuzz.ui.screens.CommonScaffold
import com.roland.android.worldbuzz.ui.screens.CommonScreen
import com.roland.android.worldbuzz.ui.screens.list.CollectionDetails
import com.roland.android.worldbuzz.utils.Constants.NavigationBarHeight
import com.roland.android.worldbuzz.utils.Constants.PADDING_WIDTH
import com.roland.android.worldbuzz.utils.Converters.toJson
import com.roland.android.worldbuzz.utils.animatePagerItem
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
	uiState: HomeUiState,
	retry: () -> Unit,
	navigate: (Screens) -> Unit
) {
	val errorMessage = rememberSaveable { mutableStateOf<String?>(null) }

	CommonScaffold(
		topBar = {
			TopAppBar(
				title = stringResource(R.string.breaking_news),
				canGoBack = false
			)
		}
	) { paddingValues ->
		CommonScreen(
			state = uiState.breakingNews,
			paddingValues = paddingValues,
			loadingScreen = { error ->
				HomeLoadingUi(error == null)
				errorMessage.value = error
			}
		) { data ->
			LazyColumn(
				modifier = Modifier.fillMaxSize(),
				contentPadding = PaddingValues(bottom = 50.dp + NavigationBarHeight),
				horizontalAlignment = Alignment.CenterHorizontally
			) {
				item {
					TrendingNewsPager(
						trendingNews = data.trendingNews,
						onItemClick = { navigate(Screens.DetailsScreen(it)) },
						onSeeMoreClick = {
							val collectionDetails = CollectionDetails(Collections.TrendingNews.name)
							navigate(Screens.ListScreen(collectionDetails.toJson()))
						}
					)
				}
				item {
					Header(
						header = stringResource(R.string.recommended),
						modifier = Modifier
							.fillMaxWidth()
							.padding(PADDING_WIDTH),
						onSeeMoreClick = {
							val collectionDetails = CollectionDetails(Collections.TrendingNews.name)
							navigate(Screens.ListScreen(collectionDetails.toJson()))
						}
					)
				}
				RecommendedNews(
					recommendedNews = data.recommendedNews,
					onItemClick = { navigate(Screens.DetailsScreen(it)) }
				)
			}
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
			if (uiState.breakingNews is State.Error) return@LaunchedEffect
			errorMessage.value = null
		}
	}
}

@Composable
private fun TrendingNewsPager(
	trendingNews: List<Article>,
	onItemClick: (ArticleJson) -> Unit,
	onSeeMoreClick: () -> Unit
) {
	val scope = rememberCoroutineScope()
	val pagerState = rememberPagerState { 20 }
	val paddingTargetValue = remember(pagerState.currentPage) {
		derivedStateOf { if (pagerState.currentPage == 0) PADDING_WIDTH else 40.dp }
	}
	val startPaddingValue by animateDpAsState(
		targetValue = paddingTargetValue.value,
		label = "padding width value"
	)
	val screenWidth = LocalConfiguration.current.screenWidthDp

	Header(
		header = stringResource(R.string.trending),
		modifier = Modifier
			.fillMaxWidth()
			.padding(PADDING_WIDTH),
		onSeeMoreClick = onSeeMoreClick
	)
	HorizontalPager(
		state = pagerState,
		contentPadding = PaddingValues(
			start = startPaddingValue,
			end = PADDING_WIDTH
		),
		modifier = Modifier
			.fillMaxWidth()
			.padding(bottom = 16.dp),
		pageSize = PageSize.Fixed((screenWidth - 64).dp),
		flingBehavior = PagerDefaults.flingBehavior(
			state = pagerState,
			pagerSnapDistance = PagerSnapDistance.atMost(trendingNews.size)
		)
	) { page ->
		val article = trendingNews[page]
		PagerPoster(
			article = article,
			modifier = Modifier.animatePagerItem(page, pagerState),
			onClick = {
				if (page != pagerState.currentPage) {
					scope.launch {
						pagerState.animateScrollToPage(
							page = page,
							animationSpec = tween(1000)
						)
					}
					return@PagerPoster
				}
				onItemClick(article.toJson())
			}
		)
	}
}

@Suppress("FunctionName")
private fun LazyListScope.RecommendedNews(
	recommendedNews: List<Article>,
	onItemClick: (ArticleJson) -> Unit
) {
	items(recommendedNews.size) {
		val article = recommendedNews[it]
		ListItem(
			article = article,
			onClick = { onItemClick(article.toJson()) }
		)
	}
}

@Preview
@Composable
private fun HomeScreenPreview() {
	MaterialTheme {
		val successState = State.Success(HomeModel(sampleNewsData, sampleNewsData))
//		val errorState = State.Error<HomeModel>("Broken Connection!")

		HomeScreen(
			uiState = HomeUiState(successState),
			retry = {},
			navigate = {}
		)
	}
}