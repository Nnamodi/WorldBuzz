package com.roland.android.worldbuzz.ui.screens.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AutoStories
import androidx.compose.material.icons.rounded.Bookmarks
import androidx.compose.material.icons.rounded.Newspaper
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.roland.android.domain.usecase.Collections
import com.roland.android.worldbuzz.R
import com.roland.android.worldbuzz.utils.Constants.SEARCH

@Composable
fun EmptyListUi(collection: String) {
	Column(
		modifier = Modifier
			.fillMaxSize()
			.alpha(0.8f)
			.padding(30.dp),
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Icon(
			imageVector = collection.icon(),
			contentDescription = stringResource(collection.text()),
			modifier = Modifier.size(125.dp)
		)
		Spacer(modifier = Modifier.height(20.dp))
		Text(
			text = stringResource(collection.text()),
			fontSize = 24.sp,
			fontWeight = FontWeight.SemiBold,
			textAlign = TextAlign.Center
		)
	}
}

private fun String.text(): Int {
	if (this == SEARCH) {
		return R.string.no_results_found
	}
	val collection = Collections.valueOf(this)
	return when (collection) {
		Collections.ReadingHistory -> R.string.nothing_read
		Collections.SavedArticles -> R.string.nothing_saved
		else -> R.string.nothing_here
	}
}

private fun String.icon(): ImageVector {
	if (this == SEARCH) {
		return Icons.Rounded.Search
	}
	val collection = Collections.valueOf(this)
	return when (collection) {
		Collections.ReadingHistory -> Icons.Rounded.AutoStories
		Collections.SavedArticles -> Icons.Rounded.Bookmarks
		else -> Icons.Rounded.Newspaper
	}
}

@Preview(showBackground = true)
@Composable
private fun EmptyListPreview() {
	MaterialTheme {
		EmptyListUi(Collections.SavedArticles.name)
	}
}