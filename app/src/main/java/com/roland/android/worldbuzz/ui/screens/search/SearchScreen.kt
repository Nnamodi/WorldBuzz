package com.roland.android.worldbuzz.ui.screens.search

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.compose.collectAsLazyPagingItems
import com.roland.android.worldbuzz.R
import com.roland.android.worldbuzz.data.State
import com.roland.android.worldbuzz.data.sampleNewsPagingData
import com.roland.android.worldbuzz.data.sampleNewsSource
import com.roland.android.worldbuzz.ui.components.appbars.NewsSearchBar
import com.roland.android.worldbuzz.ui.components.widgets.ListItems
import com.roland.android.worldbuzz.ui.components.widgets.Snackbar
import com.roland.android.worldbuzz.ui.components.widgets.SnackbarDuration
import com.roland.android.worldbuzz.ui.navigation.Screens
import com.roland.android.worldbuzz.ui.screens.CommonScaffold
import com.roland.android.worldbuzz.ui.screens.CommonScreen
import com.roland.android.worldbuzz.ui.screens.list.ListLoadingUi

@Composable
fun SearchScreen(
	uiState: SearchUiState,
	action: (SearchActions) -> Unit,
	navigate: (Screens) -> Unit
) {
	val (query, result, newsSources, selectedCategory, selectedSources) = uiState
	val errorMessage = rememberSaveable { mutableStateOf<String?>(null) }
	var newQuery by rememberSaveable { mutableStateOf(query) }
	val newCategorySelected = remember { mutableStateOf(selectedCategory) }
	var newSourcesSelected = remember { selectedSources.toMutableStateList() }
	var resultsFetched by remember { mutableStateOf(false) }
	val openFilterSheet = rememberSaveable { mutableStateOf(
		query.isEmpty()
				&& selectedCategory == null
				&& selectedSources.isEmpty()
	) }
	val onSearch = remember { {
		val pref = SearchPref(newQuery.trim(), newCategorySelected.value, newSourcesSelected)
		action(SearchActions.Search(pref))
		openFilterSheet.value = false
	} }

	CommonScaffold(
		topBar = {
			NewsSearchBar(
				searchQuery = query,
				isFilterOpen = openFilterSheet.value,
				onValueChange = { newQuery = it },
				onSearch = onSearch,
				openFilter = { openFilterSheet.value = true },
				navigate = navigate
			)
		}
	) { paddingValues ->
		CommonScreen(
			state = result,
			paddingValues = paddingValues,
			loadingScreen = { error ->
				ListLoadingUi(isLoading = error == null)
				errorMessage.value = error
				resultsFetched = false
			}
		) { articles ->
			ListItems(
				articles = articles.collectAsLazyPagingItems(),
				onItemClick = { navigate(Screens.DetailsScreen(it)) },
				onLoadError = { errorMessage.value = it }
			)
			resultsFetched = true
		}
		SearchFilterSheet(
			showSheet = openFilterSheet.value,
			searchQueryIsNotEmpty = query.isNotEmpty(),
			resultsFetched = resultsFetched,
			selectedCategory = selectedCategory,
			selectedSources = selectedSources,
			sourcesToSelect = newsSources,
			paddingValues = paddingValues,
			onApplyFilter = { categories, sources ->
				newCategorySelected.value = categories
				newSourcesSelected = sources.toMutableStateList()
				onSearch()
			},
			closeSheet = { openFilterSheet.value = false }
		)

		if (errorMessage.value != null) {
			Snackbar(
				message = errorMessage.value!!,
				paddingValues = paddingValues,
				actionLabel = stringResource(R.string.retry),
				action = { action(SearchActions.Retry) },
				duration = SnackbarDuration.Long
			)
		}

		LaunchedEffect(Unit) {
			if (result is State.Error) return@LaunchedEffect
			errorMessage.value = null
		}
	}
}

@Preview
@Composable
private fun SearchScreenPreview() {
	MaterialTheme {
		SearchScreen(
			uiState = SearchUiState(
				query = "Nato",
				result = State.Success(sampleNewsPagingData),
				newsSources = sampleNewsSource
			),
			action = {}, navigate = {}
		)
	}
}