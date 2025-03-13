package com.roland.android.worldbuzz.ui.screens.following

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.roland.android.domain.model.CategoryModel
import com.roland.android.worldbuzz.R
import com.roland.android.worldbuzz.ui.components.CategoryItem
import com.roland.android.worldbuzz.ui.components.appbars.TopAppBar
import com.roland.android.worldbuzz.ui.navigation.Screens
import com.roland.android.worldbuzz.utils.Constants.PADDING_WIDTH
import com.roland.android.worldbuzz.utils.Converters.capitalizeFirstLetter
import com.roland.android.worldbuzz.utils.Extensions.icon

@Composable
fun CategoriesScreen(
	uiState: CategoriesUiState,
	actions: (FollowingActions) -> Unit,
	navigate: (Screens) -> Unit
) {
	Scaffold(
		topBar = {
			TopAppBar(
				title = stringResource(R.string.categories),
				navigate = navigate
			)
		}
	) { paddingValues ->
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(paddingValues)
				.padding(PADDING_WIDTH)
				.verticalScroll(rememberScrollState()),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			CategoryItems(
				subscribedCategories = uiState.subscribedCategories,
				onSelect = { category, subscribe ->
					actions(FollowingActions.SubscribedToCategory(category, subscribe))
				}
			)
			Spacer(Modifier.weight(1f))
			Button(
				onClick = { navigate(Screens.Back) },
				modifier = Modifier
					.fillMaxWidth()
					.padding(50.dp, 24.dp),
				shape = MaterialTheme.shapes.small
			) {
				Text(
					text = stringResource(R.string.done),
					modifier = Modifier.padding(vertical = 6.dp),
					style = MaterialTheme.typography.titleLarge
				)
			}
		}
	}
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun CategoryItems(
	subscribedCategories: List<CategoryModel>,
	onSelect: (CategoryModel, Boolean) -> Unit
) {
	val context = LocalContext.current

	FlowRow(
		modifier = Modifier
			.fillMaxWidth()
			.wrapContentHeight(align = Alignment.Top),
		horizontalArrangement = Arrangement.spacedBy(10.dp),
		verticalArrangement = Arrangement.spacedBy(20.dp),
		maxItemsInEachRow = 2
	) {
		CategoryModel.entries.forEach {
			val subscribed = it in subscribedCategories
			CategoryItem(
				icon = it.icon(),
				name = it.category.capitalizeFirstLetter(),
				modifier = Modifier.weight(1f),
				selected = subscribed,
				iconSize = 70.dp,
				onClick = {
					if (subscribedCategories.size < 2 && subscribed) {
						Toast.makeText(
							context,
							context.getString(R.string.must_subscribe_to_one),
							Toast.LENGTH_SHORT
						).show()
						return@CategoryItem
					}
					onSelect(it, !subscribed)
				}
			)
		}
	}
}

@Preview
@Composable
private fun CategoriesScreenPreview() {
	MaterialTheme {
		val subscribedCategories = remember {
			CategoryModel.entries.takeLast(1).toMutableStateList()
		}

		CategoriesScreen(
			uiState = CategoriesUiState(subscribedCategories),
			actions = { if (it is FollowingActions.SubscribedToCategory) {
				if (it.subscribe) subscribedCategories.add(it.category)
				else subscribedCategories.remove(it.category)
			} },
			navigate = {}
		)
	}
}