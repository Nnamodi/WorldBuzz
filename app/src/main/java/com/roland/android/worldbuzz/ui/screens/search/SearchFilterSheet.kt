package com.roland.android.worldbuzz.ui.screens.search

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.roland.android.domain.model.CategoryModel
import com.roland.android.domain.model.SourceDetail
import com.roland.android.worldbuzz.R
import com.roland.android.worldbuzz.data.sampleNewsSource
import com.roland.android.worldbuzz.ui.components.SourceItem
import com.roland.android.worldbuzz.ui.components.widgets.Header
import com.roland.android.worldbuzz.utils.Constants.PADDING_WIDTH

typealias SelectedCategories = List<CategoryModel>
typealias SelectedSources = List<String>

@Composable
fun SearchFilterSheet(
	showSheet: Boolean,
	selectedCategories: List<CategoryModel>,
	selectedSources: List<String>,
	sourcesToSelect: List<SourceDetail>,
	paddingValues: PaddingValues,
	onApplyFilter: (SelectedCategories, SelectedSources) -> Unit,
	closeSheet: () -> Unit
) {
	val newCategoriesSelected = remember { selectedCategories.toMutableStateList() }
	val newSourcesSelected = remember { selectedSources.toMutableStateList() }

	AnimatedVisibility(
		visible = showSheet,
		modifier = Modifier.padding(paddingValues),
		enter = slideInVertically(tween(400)) { -it },
		exit = slideOutVertically(tween(300)) { -it }
	) {
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(horizontal = PADDING_WIDTH)
				.background(MaterialTheme.colorScheme.background),
			verticalArrangement = Arrangement.Bottom
		) {
			LazyColumn(modifier = Modifier.weight(1f)) {
				item {
					Header(
						header = stringResource(R.string.publishers),
						modifier = Modifier.padding(vertical = 12.dp),
						showSeeMore = false
					)
				}
				item {
					SourceItems(
						selectedSources = newSourcesSelected,
						sourcesToSelect = sourcesToSelect,
						onSelect = { newSourcesSelected.add(it) },
						onUnselect = { newSourcesSelected.remove(it) }
					)
				}
				item {
					Header(
						header = stringResource(R.string.categories),
						modifier = Modifier.padding(vertical = 12.dp),
						showSeeMore = false
					)
				}
				item {
					CategoryItems(
						selectedCategories = newCategoriesSelected,
						onSelect = { newCategoriesSelected.add(it) },
						onUnselect = { newCategoriesSelected.remove(it) }
					)
				}
			}
			Button(
				onClick = {
					onApplyFilter(newCategoriesSelected, newSourcesSelected)
					closeSheet()
				},
				modifier = Modifier
					.fillMaxWidth()
					.padding(50.dp, 24.dp),
				shape = RoundedCornerShape(6.dp)
			) {
				val noFilters = (selectedCategories == newCategoriesSelected) && (selectedSources == newSourcesSelected)
				Text(
					text = stringResource(if (noFilters) R.string.close else R.string.search),
					modifier = Modifier.padding(vertical = 6.dp),
					style = MaterialTheme.typography.titleLarge
				)
			}
		}
	}

	if (showSheet && (newCategoriesSelected.isNotEmpty() || newSourcesSelected.isNotEmpty())) {
		BackHandler { closeSheet() }
	}
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SourceItems(
	selectedSources: List<String>,
	sourcesToSelect: List<SourceDetail>,
	onSelect: (String) -> Unit,
	onUnselect: (String) -> Unit
) {
	FlowRow(
		modifier = Modifier
			.fillMaxWidth()
			.padding(bottom = 10.dp)
			.wrapContentHeight(align = Alignment.Top),
		verticalArrangement = Arrangement.spacedBy(20.dp),
		horizontalArrangement = Arrangement.SpaceBetween
	) {
		sourcesToSelect.forEach { source ->
			val selected = source.name in selectedSources
			SourceItem(
				iconUrl = source.iconUrl(),
				name = source.name,
				selected = selected,
				onClick = { if (selected) onUnselect(source.name) else onSelect(source.name) }
			)
		}
	}
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun CategoryItems(
	selectedCategories: List<CategoryModel>,
	onSelect: (CategoryModel) -> Unit,
	onUnselect: (CategoryModel) -> Unit
) {
	FlowRow(
		modifier = Modifier
			.fillMaxWidth()
			.wrapContentHeight(align = Alignment.Top),
		horizontalArrangement = Arrangement.spacedBy(10.dp),
		verticalArrangement = Arrangement.spacedBy(20.dp)
	) {
		CategoryModel.entries.forEach {
			val selected = it in selectedCategories
			FilterChip(
				selected = selected,
				onClick = { if (selected) onUnselect(it) else onSelect(it) },
				label = { Text(it.category) }
			)
		}
	}
}

@Preview
@Composable
private fun SearchFilterSheetPreview() {
	MaterialTheme {
		var showSheet by remember { mutableStateOf(true) }

		Surface(onClick = {showSheet = !showSheet}) {
			SearchFilterSheet(
				showSheet = showSheet,
				selectedCategories = emptyList(),
				selectedSources = emptyList(),
				sourcesToSelect = sampleNewsSource,
				paddingValues = PaddingValues(),
				onApplyFilter = { _, _ -> showSheet = false },
				closeSheet = { showSheet = false }
			)
		}
	}
}