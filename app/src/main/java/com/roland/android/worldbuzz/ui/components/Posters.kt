package com.roland.android.worldbuzz.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.AsyncImagePainter.State.Empty
import coil.request.ImageRequest
import com.roland.android.domain.model.Article
import com.roland.android.worldbuzz.R
import com.roland.android.worldbuzz.utils.painterPlaceholder

@Composable
fun PagerPoster(
	article: Article,
	modifier: Modifier = Modifier,
	onClick: () -> Unit
) {
	Box(
		modifier = modifier
			.fillMaxWidth()
			.height(230.dp)
			.clickable { onClick() }
	) {
		Poster(
			model = article.imageUrl,
			contentDescription = article.title,
			modifier = Modifier.fillMaxSize()
		)
		Text(
			text = article.title,
			modifier = Modifier
				.align(Alignment.BottomStart)
				.padding(20.dp),
			color = Color.White,
			fontSize = 20.sp,
			fontWeight = FontWeight.SemiBold,
			overflow = TextOverflow.Ellipsis
		)
	}
}

@Composable
fun DetailsPoster(
	article: Article,
	modifier: Modifier = Modifier
) {
	Poster(
		model = article.imageUrl,
		contentDescription = article.title,
		modifier = modifier
			.fillMaxWidth()
			.fillMaxHeight(0.3f)
	)
}

@Composable
fun ListPoster(
	article: Article,
	modifier: Modifier = Modifier
) {
	Poster(
		model = article.imageUrl,
		contentDescription = article.title,
		modifier = modifier.size(120.dp, 130.dp)
	)
}

@Composable
private fun Poster(
	model: String,
	contentDescription: String?,
	modifier: Modifier
) {
	val state = remember { mutableStateOf<AsyncImagePainter.State>(Empty) }

	Box(
		modifier = modifier.clip(MaterialTheme.shapes.large)
	) {
		AsyncImage(
			model = ImageRequest.Builder(LocalContext.current)
				.data(model)
				.crossfade(true)
				.build(),
			contentDescription = contentDescription,
			modifier = Modifier
				.fillMaxSize()
				.painterPlaceholder(state.value),
			onState = { state.value = it },
			contentScale = ContentScale.Crop
		)
	}
}

@Composable
fun SourceItem(
	iconUrl: String,
	name: String,
	selected: Boolean,
	size: Dp = 80.dp,
	onClick: () -> Unit
) {
	val state = remember { mutableStateOf<AsyncImagePainter.State>(Empty) }

	Box(
		modifier = Modifier
			.clip(CircleShape)
			.size(size)
			.clickable { onClick() }
			.alpha(if (selected) 0.5f else 1f),
		contentAlignment = Alignment.Center
	) {
		AsyncImage(
			model = iconUrl,
			contentDescription = name,
			modifier = Modifier
				.fillMaxSize()
				.painterPlaceholder(state.value),
			onState = { state.value = it },
			contentScale = ContentScale.Crop
		)
		if (selected) {
			Icon(
				imageVector = Icons.Rounded.Check,
				contentDescription = stringResource(R.string.selected, name),
				modifier = Modifier.size(40.dp),
				tint = Color.Green
			)
		}
	}
}

@Preview
@Composable
private fun SourceItemPreview() {
	MaterialTheme {
		SourceItem(iconUrl = "", name = "", selected = false) {}
	}
}