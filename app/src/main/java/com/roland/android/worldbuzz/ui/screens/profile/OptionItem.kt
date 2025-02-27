package com.roland.android.worldbuzz.ui.screens.profile

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Bookmarks
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.roland.android.worldbuzz.utils.Constants.PADDING_WIDTH

@Composable
fun OptionItem(
	icon: ImageVector,
	label: String,
	modifier: Modifier = Modifier,
	subLabel: String? = null,
	onClick: () -> Unit
) {
	Row(
		modifier = modifier
			.fillMaxWidth()
			.padding(PADDING_WIDTH, 4.dp)
			.clip(shapes.extraSmall)
			.border(2.dp, colorScheme.surfaceDim.copy(alpha = 0.75f), shapes.small)
			.clickable { onClick() }
			.padding(20.dp),
		verticalAlignment = Alignment.CenterVertically
	) {
		Icon(
			imageVector = icon,
			contentDescription = label,
			modifier = Modifier.padding(end = 10.dp)
		)
		Column(
			modifier = Modifier
				.weight(1f)
				.padding(end = 10.dp)
		) {
			Text(
				text = label,
				fontSize = 20.sp,
				style = typography.bodyLarge
			)
			subLabel?.let {
				Text(
					text = it,
					modifier = Modifier.alpha(0.5f),
					lineHeight = 16.sp,
					style = typography.bodyLarge
				)
			}
		}
	}
}

@Preview(showBackground = true)
@Composable
private fun OptionItemPreview() {
	MaterialTheme {
		OptionItem(icon = Icons.Rounded.Bookmarks, label = "Save Article") {}
	}
}