package com.roland.android.worldbuzz.ui.screens.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.roland.android.worldbuzz.ui.components.widgets.DotSeparator
import com.roland.android.worldbuzz.utils.ShimmerBoxItem

@Composable
fun ListLoadingUi(
	isLoading: Boolean,
	modifier: Modifier = Modifier
) {
	Column(
		modifier = modifier
			.fillMaxSize()
			.verticalScroll(rememberScrollState())
	) {
		repeat(10) {
			ListLoadingItem(isLoading)
		}
	}
}

@Composable
fun ListLoadingItem(
	isLoading: Boolean,
	modifier: Modifier = Modifier
) {
	Row(
		modifier = modifier
			.fillMaxWidth()
			.height(140.dp + 24.dp)
			.padding(12.dp)
			.padding(horizontal = 4.dp),
		verticalAlignment = Alignment.CenterVertically
	) {
		ShimmerBoxItem(
			isLoading = isLoading,
			modifier = modifier.size(120.dp, 130.dp)
		)
		Column(Modifier.padding(12.dp)) {
			ShimmerBoxItem(
				isLoading = isLoading,
				modifier = modifier
					.alpha(0.5f)
					.size(40.dp, 14.dp)
					.padding(vertical = 3.dp)
			)
			ShimmerBoxItem(
				isLoading = isLoading,
				modifier = modifier
					.fillMaxWidth()
					.weight(1f)
					.padding(vertical = 6.dp)
			)
			ShimmerBoxItem(
				isLoading = isLoading,
				modifier = modifier
					.fillMaxWidth(0.6f)
					.weight(1f)
					.padding(vertical = 6.dp)
			)
			Row(
				modifier = Modifier
					.fillMaxWidth()
					.alpha(0.5f),
				verticalAlignment = Alignment.CenterVertically
			) {
				ShimmerBoxItem(
					isLoading = isLoading,
					modifier = modifier
						.size(40.dp, 14.dp)
						.padding(vertical = 3.dp)
				)
				DotSeparator()
				ShimmerBoxItem(
					isLoading = isLoading,
					modifier = modifier
						.size(40.dp, 14.dp)
						.padding(vertical = 3.dp)
				)
			}
		}
	}
}

@Preview(showBackground = true)
@Composable
private fun ListLoadingItemPreview() {
	MaterialTheme {
		ListLoadingItem(isLoading = true)
	}
}

@Preview(showBackground = true)
@Composable
private fun ListLoadingUiPreview() {
	MaterialTheme {
		ListLoadingUi(isLoading = true)
	}
}