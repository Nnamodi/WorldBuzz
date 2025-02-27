package com.roland.android.worldbuzz.ui.screens.profile

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AutoStories
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Newspaper
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.roland.android.domain.usecase.Collections
import com.roland.android.worldbuzz.R
import com.roland.android.worldbuzz.ui.components.appbars.TopAppBar
import com.roland.android.worldbuzz.ui.components.widgets.Header
import com.roland.android.worldbuzz.ui.navigation.Screens
import com.roland.android.worldbuzz.ui.screens.list.CollectionDetails
import com.roland.android.worldbuzz.ui.screens.profile.ProfileOptions.Categories
import com.roland.android.worldbuzz.ui.screens.profile.ProfileOptions.Publishers
import com.roland.android.worldbuzz.ui.screens.profile.ProfileOptions.ReadHistory
import com.roland.android.worldbuzz.ui.screens.profile.ProfileOptions.SavedArticles
import com.roland.android.worldbuzz.ui.screens.profile.ProfileOptions.Settings
import com.roland.android.worldbuzz.utils.Constants.PADDING_WIDTH
import com.roland.android.worldbuzz.utils.Converters.toJson

@Composable
fun ProfileScreen(navigate: (Screens) -> Unit) {
	Scaffold(
		topBar = {
			TopAppBar(
				title = stringResource(R.string.profile),
				canGoBack = false
			)
		}
	) { paddingValues ->
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(paddingValues)
				.verticalScroll(rememberScrollState())
		) {
			UserInfo {}
			OptionItem(
				icon = Settings.icon,
				label = stringResource(Settings.label),
				onClick = { navigate(Screens.SettingsScreen) }
			)
			Header(
				header = stringResource(R.string.your_stats),
				modifier = Modifier.padding(start = PADDING_WIDTH, top = PADDING_WIDTH)
			)
			OptionItem(
				icon = Categories.icon,
				label = stringResource(Categories.label),
				onClick = { navigate(Screens.CategoryScreen) }
			)
			OptionItem(
				icon = Publishers.icon,
				label = stringResource(Publishers.label),
				onClick = { navigate(Screens.SourcesScreen) }
			)
			Header(
				header = stringResource(R.string.account),
				modifier = Modifier.padding(start = PADDING_WIDTH, top = PADDING_WIDTH)
			)
			OptionItem(
				icon = SavedArticles.icon,
				label = stringResource(SavedArticles.label),
				onClick = {
					val collectionDetails = CollectionDetails(Collections.SavedArticles.name)
					navigate(Screens.ListScreen(collectionDetails.toJson()))
				}
			)
			OptionItem(
				icon = ReadHistory.icon,
				label = stringResource(ReadHistory.label),
				onClick = {
					val collectionDetails = CollectionDetails(Collections.ReadingHistory.name)
					navigate(Screens.ListScreen(collectionDetails.toJson()))
				}
			)
			Spacer(Modifier.height(100.dp))
		}
	}
}

@Composable
private fun UserInfo(onEditClicked: () -> Unit) {
	Row(
		modifier = Modifier.padding(PADDING_WIDTH, 20.dp),
		verticalAlignment = Alignment.CenterVertically
	) {
		Icon(
			imageVector = Icons.Rounded.AccountCircle,
			contentDescription = stringResource(R.string.profile_image),
			modifier = Modifier.size(80.dp),
			tint = Color.LightGray
		)
		Column(
			modifier = Modifier
				.weight(1f)
				.padding(horizontal = 8.dp)
		) {
			Text(
				text = "Nnamdi Igede",
				modifier = Modifier.padding(bottom = 4.dp),
				overflow = TextOverflow.Ellipsis,
				softWrap = false,
				style = MaterialTheme.typography.headlineSmall
			)
			Text(
				text = "rolandnnamodi@gmail.com",
				modifier = Modifier.alpha(0.5f),
				overflow = TextOverflow.Ellipsis,
				softWrap = false,
				style = MaterialTheme.typography.bodyLarge
			)
		}
		Row(
			modifier = Modifier
				.clip(MaterialTheme.shapes.medium)
				.clickable { onEditClicked() }
				.padding(6.dp)
				.alpha(0.8f),
			verticalAlignment = Alignment.CenterVertically
		) {
			Icon(
				imageVector = Icons.Rounded.Edit,
				contentDescription = stringResource(R.string.edit_profile),
				modifier = Modifier.size(16.dp)
			)
			Text(
				text = stringResource(R.string.edit),
				modifier = Modifier.padding(start = 4.dp)
			)
		}
	}
}

private enum class ProfileOptions(
	val icon: ImageVector,
	@StringRes val label: Int
) {
	Settings(
		icon = Icons.Outlined.Settings,
		label = R.string.settings
	),
	Categories(
		icon = Icons.Outlined.Category,
		label = R.string.categories
	),
	Publishers(
		icon = Icons.Outlined.Newspaper,
		label = R.string.publishers
	),
	SavedArticles(
		icon = Icons.Outlined.Bookmarks,
		label = R.string.saved_articles
	),
	ReadHistory(
		icon = Icons.Outlined.AutoStories,
		label = R.string.reading_history
	)
}

@Preview
@Composable
private fun ProfileScreenPreview() {
	MaterialTheme {
		ProfileScreen {}
	}
}