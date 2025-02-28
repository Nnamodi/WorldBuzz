package com.roland.android.worldbuzz.ui.components.appbars

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.rounded.Explore
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.PersonOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.roland.android.worldbuzz.R
import com.roland.android.worldbuzz.ui.navigation.AppRoute

@Composable
fun NavBar(
	navController: NavHostController,
	modifier: Modifier = Modifier
) {
	val navBackStackEntry = navController.currentBackStackEntryAsState()
	val currentDestination = navBackStackEntry.value?.destination?.route
	val currentlyInStartScreen = NavBarItems.entries.any {
		currentDestination == it.route
	}
	val showBottomBar by remember(
		currentlyInStartScreen,
		currentDestination
	) {
		mutableStateOf(currentlyInStartScreen)
	}

	AnimatedVisibility(
		visible = showBottomBar,
		enter = slideInVertically(
			animationSpec = tween(durationMillis = 350, delayMillis = 1000),
			initialOffsetY = { it }
		),
		exit = ExitTransition.None
	) { NavBarVisuals(navController, modifier) }
}

@SuppressLint("RestrictedApi")
@Composable
private fun NavBarVisuals(
	navController: NavHostController,
	modifier: Modifier = Modifier
) {
	val navBackStackEntry = navController.currentBackStackEntryAsState()
	val currentRoute = navBackStackEntry.value?.destination?.route
	val backStack = navController.currentBackStack.collectAsState().value.map { it.destination.route }

	Row(
		modifier = modifier
			.fillMaxWidth()
			.padding(16.dp)
			.clip(MaterialTheme.shapes.large)
			.background(NavigationBarDefaults.containerColor),
		horizontalArrangement = Arrangement.Center,
		verticalAlignment = Alignment.CenterVertically
	) {
		NavBarItems.entries.forEach { item ->
			val inBackStack = item.route == currentRoute || item.route in backStack
			val selected = when (item) {
				NavBarItems.Home -> {
					val noHomeScreenInStack = NavBarItems.entries.filter { it != NavBarItems.Home }
						.all { it.route !in backStack }
					inBackStack && noHomeScreenInStack
				}
				else -> inBackStack
			}

			NavigationBarItem(
				selected = selected,
				selectedIcon = item.selectedIcon,
				unselectedIcon = item.unselectedIcon,
				label = stringResource(item.title),
				modifier = Modifier.weight(1f),
				onClick = {
					navController.navigate(item.route) {
						popUpTo(navController.graph.findStartDestination().id) {
							saveState = item.route != currentRoute
						}
						launchSingleTop = true
						restoreState = true
					}
				}
			)
		}
	}
}

@Composable
private fun NavigationBarItem(
	selected: Boolean,
	selectedIcon: ImageVector,
	unselectedIcon: ImageVector,
	label: String,
	modifier: Modifier = Modifier,
	onClick: () -> Unit
) {
	val color = if (selected) colorScheme.primary else colorScheme.onBackground

	Column(
		modifier = modifier
			.fillMaxWidth()
			.clickable { onClick() }
			.padding(vertical = 6.dp),
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Icon(
			imageVector = if (selected) selectedIcon else unselectedIcon,
			contentDescription = label,
			modifier = Modifier.padding(start = 12.5.dp, end = 12.5.dp, bottom = 4.dp),
			tint = color
		)
		Text(text = label, color = color)
	}
}

private enum class NavBarItems(
	@StringRes val title: Int,
	val selectedIcon: ImageVector,
	val unselectedIcon: ImageVector,
	val route: String
) {
	Home(
		title = R.string.home,
		selectedIcon = Icons.Rounded.Home,
		unselectedIcon = Icons.Outlined.Home,
		route = AppRoute.HomeScreen.route
	),
	Discover(
		title = R.string.discover,
		selectedIcon = Icons.Rounded.Explore,
		unselectedIcon = Icons.Outlined.Explore,
		route = AppRoute.DiscoverScreen.route
	),
	Profile(
		title = R.string.profile,
		selectedIcon = Icons.Rounded.Person,
		unselectedIcon = Icons.Rounded.PersonOutline,
		route = AppRoute.ProfileScreen.route
	)
}