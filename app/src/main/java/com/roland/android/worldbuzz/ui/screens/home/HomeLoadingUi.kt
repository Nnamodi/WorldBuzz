package com.roland.android.worldbuzz.ui.screens.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.roland.android.worldbuzz.R
import com.roland.android.worldbuzz.ui.components.widgets.Header
import com.roland.android.worldbuzz.ui.screens.list.ListLoadingItem
import com.roland.android.worldbuzz.utils.Constants.NavigationBarHeight
import com.roland.android.worldbuzz.utils.Constants.PADDING_WIDTH
import com.roland.android.worldbuzz.utils.ShimmerBoxItem

@Composable
fun HomeLoadingUi(isLoading: Boolean) {
	LazyColumn(
		contentPadding = PaddingValues(
			bottom = 50.dp + NavigationBarHeight
		)
	) {
		item {
			Header(
				header = stringResource(R.string.trending),
				modifier = Modifier.padding(PADDING_WIDTH),
				isLoadingUi = true) {}
		}
		item {
			ShimmerBoxItem(
				isLoading = isLoading,
				modifier = Modifier
					.fillMaxWidth()
					.height(230.dp)
					.padding(horizontal = PADDING_WIDTH)
					.padding(bottom = PADDING_WIDTH)
			)
		}
		item {
			Header(
				header = stringResource(R.string.recommended),
				modifier = Modifier.padding(PADDING_WIDTH),
				isLoadingUi = true
			) {}
		}
		items(5) {
			ListLoadingItem(isLoading = isLoading)
		}
	}
}

@Preview(showBackground = true)
@Composable
private fun HomeLoadingUiPreview() {
	MaterialTheme {
		HomeLoadingUi(isLoading = true)
	}
}