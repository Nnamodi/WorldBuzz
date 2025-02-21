package com.roland.android.worldbuzz.ui.screens.list

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.roland.android.domain.model.Article
import com.roland.android.domain.usecase.Collections
import com.roland.android.worldbuzz.R
import com.roland.android.worldbuzz.data.State
import com.roland.android.worldbuzz.data.sampleNewsPagingData
import com.roland.android.worldbuzz.ui.components.appbars.TopAppBar
import com.roland.android.worldbuzz.ui.components.widgets.ListItems
import com.roland.android.worldbuzz.ui.components.widgets.Snackbar
import com.roland.android.worldbuzz.ui.components.widgets.SnackbarDuration
import com.roland.android.worldbuzz.ui.navigation.Screens
import com.roland.android.worldbuzz.ui.screens.CommonScaffold
import com.roland.android.worldbuzz.ui.screens.CommonScreen
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun ListScreen(
	state: State<MutableStateFlow<PagingData<Article>>>?,
	collectionDetails: CollectionDetails,
	action: (ListActions) -> Unit,
	navigate: (Screens) -> Unit
) {
	val errorMessage = rememberSaveable { mutableStateOf<String?>(null) }

	CommonScaffold(
		topBar = {
			TopAppBar(
				title = collectionDetails.getTitle(LocalContext.current),
				navigate = navigate
			)
		}
	) { paddingValues ->
		CommonScreen(
			state = state,
			paddingValues = paddingValues,
			loadingScreen = { error ->
				ListLoadingUi(isLoading = error == null)
				errorMessage.value = error
			}
		) { articles ->
			ListItems(
				articles = articles.collectAsLazyPagingItems(),
				onItemClick = { navigate(Screens.DetailsScreen(it)) },
				onLoadError = { errorMessage.value = it }
			)
		}

		if (errorMessage.value != null) {
			Snackbar(
				message = errorMessage.value!!,
				paddingValues = paddingValues,
				actionLabel = stringResource(R.string.retry),
				action = { action(ListActions.Retry) },
				duration = SnackbarDuration.Indefinite
			)
		}

		LaunchedEffect(Unit) {
			if (state is State.Error) return@LaunchedEffect
			errorMessage.value = null
		}
	}
}

@Preview
@Composable
private fun ListScreenPreview() {
	MaterialTheme {
		ListScreen(
			state = State.Success(sampleNewsPagingData),
			collectionDetails = CollectionDetails(Collections.RecommendedNews.name),
			action = {},
			navigate = {}
		)
	}
}