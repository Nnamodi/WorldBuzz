package com.roland.android.worldbuzz.ui.components.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.roland.android.worldbuzz.R

@Composable
fun Header(
	header: String,
	modifier: Modifier
) {
	Text(
		text = header,
		modifier = modifier.padding(4.dp, 8.dp),
		fontWeight = FontWeight.Bold,
		fontSize = 20.sp
	)
}

@Composable
fun Header(
	header: String,
	modifier: Modifier,
	showSeeMore: Boolean = true,
	onSeeMoreClick: () -> Unit = {}
) {
	Row(
		modifier = modifier,
		verticalAlignment = Alignment.CenterVertically
	) {
		Box(
			modifier = Modifier
				.padding(vertical = 8.dp)
				.size(4.dp, 22.dp)
				.clip(MaterialTheme.shapes.medium)
				.background(MaterialTheme.colorScheme.surfaceTint)
		)
		Text(
			text = header,
			modifier = Modifier.padding(horizontal = 4.dp),
			fontWeight = FontWeight.Bold,
			fontSize = 20.sp
		)
		Spacer(Modifier.weight(1f))
		if (showSeeMore) {
			Text(
				text = stringResource(R.string.more),
				modifier = Modifier
					.padding(horizontal = 4.dp)
					.clickable { onSeeMoreClick() },
				color = MaterialTheme.colorScheme.surfaceTint,
				fontWeight = FontWeight.Bold,
				fontSize = 16.sp
			)
		}
	}
}