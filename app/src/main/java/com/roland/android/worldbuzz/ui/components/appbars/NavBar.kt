package com.roland.android.worldbuzz.ui.components.appbars

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.rounded.Explore
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.PersonOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.roland.android.worldbuzz.R
import com.roland.android.worldbuzz.ui.navigation.AppRoute

@Composable
fun NavBar(navController: NavHostController) {
	val navBackStackEntry = navController.currentBackStackEntryAsState()
	val currentDestination = navBackStackEntry.value?.destination?.route
	val currentlyInStartScreen = NavBarItems.entries.any {
		currentDestination == it.route
	}
	val showBottomBar by remember(
		currentlyInStartScreen,
		currentDestination
	) {
		derivedStateOf {
			currentlyInStartScreen &&
					(currentDestination == NavBarItems.Home.route)
		}
	}

	AnimatedVisibility(
		visible = showBottomBar,
		enter = slideInVertically(
			animationSpec = tween(durationMillis = 350, delayMillis = 1000),
			initialOffsetY = { it }
		),
		exit = ExitTransition.None
	) { BottomNavBar(navController) }
}

@Composable
private fun BottomNavBar(navController: NavHostController) {
	NavigationBar(
		containerColor = NavigationBarDefaults.containerColor.copy(alpha = 0.9f)
	) {
		NavBarItems.entries.forEach { item ->
			val navBackStackEntry = navController.currentBackStackEntryAsState()
			val currentDestination = navBackStackEntry.value?.destination?.route
			val selected = currentDestination == item.route

			NavigationBarItem(
				selected = selected,
				onClick = {
					navController.navigate(item.route) {
						popUpTo(navController.graph.findStartDestination().id) {
							saveState = item.route != currentDestination
						}
						launchSingleTop = true
						restoreState = true
					}
				},
				icon = {
					Icon(if (selected) item.selectedIcon else item.unselectedIcon, null)
				},
				label = { Text(stringResource(item.title)) },
				alwaysShowLabel = true
			)
		}
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