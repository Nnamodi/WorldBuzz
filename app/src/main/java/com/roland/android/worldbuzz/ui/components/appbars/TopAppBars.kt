package com.roland.android.worldbuzz.ui.components.appbars

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.BookmarkBorder
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.roland.android.worldbuzz.R
import com.roland.android.worldbuzz.ui.navigation.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
	title: String,
	canGoBack: Boolean = true,
	navigate: (Screens) -> Unit = {}
) {
	CenterAlignedTopAppBar(
		title = { Text(text = title, fontWeight = FontWeight.Bold) },
		navigationIcon = {
			if (canGoBack) {
				IconButton(onClick = { navigate(Screens.Back) }) {
					Icon(Icons.Rounded.ArrowBackIosNew, stringResource(R.string.back))
				}
			}
		}
	)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsTopAppBar(
	title: String,
	articleSaved: Boolean,
	saveArticle: (Boolean) -> Unit,
	seeMore: () -> Unit,
	navigate: (Screens) -> Unit
) {
	LargeTopAppBar(
		title = { Text(text = title) },
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