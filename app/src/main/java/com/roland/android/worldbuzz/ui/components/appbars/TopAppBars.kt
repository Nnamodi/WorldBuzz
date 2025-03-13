package com.roland.android.worldbuzz.ui.components.appbars

import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.BookmarkBorder
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Tune
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.roland.android.domain.model.Article
import com.roland.android.worldbuzz.R
import com.roland.android.worldbuzz.ui.components.DetailsPoster
import com.roland.android.worldbuzz.ui.navigation.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
	title: String,
	canGoBack: Boolean = true,
	navigate: ((Screens) -> Unit)? = null
) {
	if (canGoBack) {
		requireNotNull(navigate) { "Must implement navigate() function if canGoBack" }
	}
	CenterAlignedTopAppBar(
		title = { Text(text = title, fontWeight = FontWeight.Bold) },
		navigationIcon = {
			if (canGoBack) {
				IconButton(onClick = { navigate?.invoke(Screens.Back) }) {
					Icon(Icons.Rounded.ArrowBackIosNew, stringResource(R.string.back))
				}
			}
		}
	)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsSearchBar(
	searchQuery: String,
	isFilterOpen: Boolean,
	onValueChange: (String) -> Unit,
	onSearch: () -> Unit,
	openFilter: () -> Unit,
	navigate: (Screens) -> Unit
) {
	var query by rememberSaveable { mutableStateOf(searchQuery) }
	val keyboard = LocalSoftwareKeyboardController.current

	CenterAlignedTopAppBar(
		title = {
			OutlinedTextField(
				value = query,
				onValueChange = { query = it; onValueChange(it) },
				modifier = Modifier
					.border(6.dp, colorScheme.background, CircleShape)
					.clip(CircleShape)
					.background(colorScheme.surfaceContainerLow),
				textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
				placeholder = {
					Row(
						modifier = Modifier.alpha(0.5f),
						verticalAlignment = Alignment.CenterVertically
					) {
						Icon(Icons.Rounded.Search, null, Modifier.padding(end = 10.dp))
						Text(stringResource(R.string.search_placeholder), Modifier.basicMarquee())
					}
				},
				keyboardOptions = KeyboardOptions(
					capitalization = KeyboardCapitalization.Words,
					autoCorrectEnabled = true,
					imeAction = ImeAction.Search
				),
				keyboardActions = KeyboardActions(
					onSearch = {
						keyboard?.hide()
						if (query.isEmpty() || query == searchQuery) return@KeyboardActions
						onSearch()
					}
				),
				shape = CircleShape,
				singleLine = true
			)
		},
		navigationIcon = {
			IconButton(onClick = { navigate(Screens.Back) }) {
				Icon(Icons.Rounded.ArrowBackIosNew, stringResource(R.string.back))
			}
		},
		actions = {
			if (!isFilterOpen) {
				IconButton(onClick = openFilter) {
					Icon(Icons.Rounded.Tune, stringResource(R.string.filter))
				}
			}
		}
	)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsTopAppBar(
	article: Article,
	articleSaved: Boolean,
	saveArticle: (Boolean) -> Unit,
	seeMore: () -> Unit,
	navigate: (Screens) -> Unit
) {
	Box {
		DetailsPoster(article)
		LargeTopAppBar(
			title = { Text(text = article.title) },
			navigationIcon = {
				IconButton(onClick = { navigate(Screens.Back) }) {
					Icon(Icons.Rounded.ArrowBackIosNew, stringResource(R.string.back))
				}
			},
			actions = {
				IconButton(onClick = { saveArticle(!articleSaved) }) {
					Icon(
						imageVector = if (articleSaved) Icons.Rounded.Bookmark else Icons.Rounded.BookmarkBorder,
						contentDescription = stringResource(if (articleSaved) R.string.unsave else R.string.save)
					)
				}
				IconButton(onClick = seeMore) {
					Icon(Icons.Rounded.MoreVert, stringResource(R.string.more))
				}
			}
		)
	}
}