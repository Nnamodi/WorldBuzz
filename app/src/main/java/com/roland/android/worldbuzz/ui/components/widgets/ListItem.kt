package com.roland.android.worldbuzz.ui.components.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.roland.android.domain.model.Article
import com.roland.android.worldbuzz.R
import com.roland.android.worldbuzz.data.sampleNewsData
import com.roland.android.worldbuzz.ui.components.ListPoster
import com.roland.android.worldbuzz.ui.screens.list.ListLoadingUi
import com.roland.android.worldbuzz.utils.Constants.NavigationBarHeight
import com.roland.android.worldbuzz.utils.Converters.toJson

typealias ArticleJson = String

@Composable
fun ListItems(
	articles: LazyPagingItems<Article>,
	modifier: Modifier = Modifier,
	onItemClick: (ArticleJson) -> Unit,
	onLoadError: (String?) -> Unit
) {
	LazyColumn(
		modifier = modifier.fillMaxHeight(),
		contentPadding = PaddingValues(bottom = NavigationBarHeight + 50.dp)
	) {
		items(articles.itemCount) { index ->
			articles[index]?.let { article ->
				ListItem(
					article = article,
					onClick = { onItemClick(article.toJson()) }
				)
			}
		}
		articles.apply {
			when (loadState.refresh) {
				LoadState.Loading -> {
					item { ListLoadingUi(isLoading = true) }
				}
				is LoadState.Error -> {
					item { ListLoadingUi(isLoading = false) }
					val errorMessage = (loadState.refresh as LoadState.Error).error.localizedMessage
					onLoadError(errorMessage)
				}
				is LoadState.NotLoading -> {}
			}
		}
		item { articles.appendStateUi() }
	}
}

@Composable
fun ListItem(
	article: Article,
	modifier: Modifier = Modifier,
	onClick: () -> Unit
) {
	Row(
		modifier = modifier
			.fillMaxWidth()
			.height(140.dp + 24.dp)
			.clickable { onClick() }
			.padding(12.dp)
			.padding(horizontal = 4.dp),
		verticalAlignment = Alignment.CenterVertically
	) {
		ListPoster(article)
		Column(Modifier.padding(12.dp)) {
			Text(
				text = article.source.name,
				modifier = Modifier.alpha(0.5f),
				style = MaterialTheme.typography.labelLarge
			)
			Text(
				text = article.title,
				modifier = Modifier.weight(1f),
				overflow = TextOverflow.Ellipsis,
				style = MaterialTheme.typography.titleLarge
			)
			Row(
				modifier = Modifier
					.fillMaxWidth()
					.alpha(0.5f),
				verticalAlignment = Alignment.CenterVertically
			) {
				Text(
					text = stringResource(R.string.author, article.author),
					overflow = TextOverflow.Ellipsis,
					style = MaterialTheme.typography.labelLarge
				)
				DotSeparator()
				Text(
					text = article.publishedAt,
					style = MaterialTheme.typography.labelLarge
				)
			}
		}
	}
}

@Composable
fun DotSeparator() {
	Box(modifier = Modifier
		.padding(horizontal = 12.dp)
		.size(4.dp)
		.clip(CircleShape)
		.background(MaterialTheme.colorScheme.outline)
	)
}

@Composable
private fun LazyPagingItems<Article>.appendStateUi() = apply {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.padding(20.dp)
			.padding(top = 50.dp),
		horizontalArrangement = Arrangement.Center
	) {
		when (loadState.append) {
			LoadState.Loading -> {
				CircularProgressIndicator(Modifier.size(30.dp))
			}
			is LoadState.Error -> {
				OutlinedButton(onClick = ::retry) {
					Text(stringResource(R.string.more))
				}
			}
			is LoadState.NotLoading -> {}
		}
	}
}

@Preview(showBackground = true)
@Composable
private fun ListItemPreview() {
	MaterialTheme {
		ListItem(
			article = sampleNewsData[0],
			onClick = {}
		)
	}
}