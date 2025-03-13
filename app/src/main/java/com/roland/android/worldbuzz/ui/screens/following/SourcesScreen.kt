package com.roland.android.worldbuzz.ui.screens.following

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.RadioButtonUnchecked
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.roland.android.domain.model.Source
import com.roland.android.domain.model.SourceDetail
import com.roland.android.worldbuzz.R
import com.roland.android.worldbuzz.data.sampleNewsSource
import com.roland.android.worldbuzz.data.sampleNewsSourceI
import com.roland.android.worldbuzz.ui.components.SourceItemPoster
import com.roland.android.worldbuzz.ui.components.appbars.TopAppBar
import com.roland.android.worldbuzz.ui.navigation.Screens
import com.roland.android.worldbuzz.utils.Constants.PADDING_WIDTH

@Composable
fun SourcesScreen(
	uiState: SourcesUiState,
	actions: (FollowingActions) -> Unit,
	navigate: (Screens) -> Unit
) {
	Scaffold(
		topBar = {
			TopAppBar(
				title = stringResource(R.string.publishers),
				navigate = navigate
			)
		}
	) { paddingValues ->
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(paddingValues)
				.padding(horizontal = PADDING_WIDTH),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			SourceItems(
				allSources = uiState.allSources,
				modifier = Modifier.weight(1f),
				onSelect = { source, subscribe ->
					actions(FollowingActions.SubscribedToSource(source, subscribe))
				}
			)
			Button(
				onClick = { navigate(Screens.Back) },
				modifier = Modifier
					.fillMaxWidth()
					.padding(50.dp, 24.dp),
				shape = shapes.small
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

@Composable
private fun SourceItems(
	allSources: List<SourceDetail>,
	modifier: Modifier,
	onSelect: (SourceDetail, Boolean) -> Unit
) {
	LazyColumn(
		modifier = modifier,
		contentPadding = PaddingValues(bottom = 50.dp)
	) {
		items(allSources.size) { index ->
			val source = allSources[index]
			SourceItem(
				source = source,
				subscribed = source.subscribed,
				onClick = { onSelect(source, !source.subscribed) }
			)
		}
	}
}

@Composable
private fun SourceItem(
	source: SourceDetail,
	modifier: Modifier = Modifier,
	subscribed: Boolean,
	onClick: () -> Unit
) {
	Row(
		modifier = modifier
			.fillMaxWidth()
			.padding(4.dp)
			.clip(shapes.extraSmall)
			.border(2.dp, colorScheme.surfaceDim.copy(alpha = 0.75f), shapes.small)
			.clickable { onClick() }
			.padding(20.dp),
		verticalAlignment = Alignment.CenterVertically
	) {
		SourceItemPoster(
			iconUrl = source.iconUrl(),
			name = source.name
		)
		Text(
			text = source.name,
			modifier = Modifier
				.weight(1f)
				.padding(horizontal = 10.dp),
			overflow = TextOverflow.Ellipsis,
			softWrap = false,
			style = MaterialTheme.typography.titleMedium
		)
		Icon(
			imageVector = if (subscribed) Icons.Rounded.CheckCircle else Icons.Rounded.RadioButtonUnchecked,
			contentDescription = stringResource(
				if (subscribed) R.string.selected else R.string.unselected,
				source.name
			),
			modifier = Modifier.size(28.dp)
		)
	}
}

@Preview
@Composable
private fun SourcesScreenPreview() {
	MaterialTheme {
		val subscribedSources = remember {
			sampleNewsSourceI.take(2).toMutableStateList()
		}
		val c: (SourceDetail) -> Source = {
			Source(name = it.name)
		}

		SourcesScreen(
			uiState = SourcesUiState(sampleNewsSource),
			actions = { if (it is FollowingActions.SubscribedToSource) {
				if (it.subscribe) subscribedSources.add(c(it.source))
				else subscribedSources.remove(c(it.source))
			} },
			navigate = {}
		)
	}
}